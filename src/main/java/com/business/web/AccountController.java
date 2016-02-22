package com.business.web;

import com.business.domain.Customer;
import com.business.domain.RoleMenu;
import com.business.service.CustomerService;
import com.business.service.CustomerServiceImpl;
import com.business.service.RoleMenuService;
import com.business.service.RoleMenuServiceImpl;
import com.business.tools.HttpSMSHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by AlexBob on 2015-03-24.
 */
@RestController
public class AccountController extends BaseController {


    private BCryptPasswordEncoder bCryptEncoder;

    private RoleMenuService menuService;

    private CustomerService customerService;

    private HttpSMSHelper smsHelper;

    @Autowired
    public AccountController(HttpSMSHelper smsHelper,RoleMenuServiceImpl menuService,
                             BCryptPasswordEncoder bCryptEncoder,
                             CustomerServiceImpl customerService) {
        this.smsHelper=smsHelper;
        this.menuService = menuService;
        this.bCryptEncoder = bCryptEncoder;
        this.customerService = customerService;
    }


    @RequestMapping("/initmenu")
    public Collection<RoleMenu> initMenu() {
        return menuService.loadMenuByDefaultAll();
    }


    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/loadmenuall")
    public Collection<RoleMenu> findMenuAll() {
        return menuService.findAllMenus();
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/loaduserall")
    public List<String> findUserAll() {
        List<String> groups = customerService.findAllGroups();
        List<String> users = new ArrayList<>();
        if (groups.size() > 0) {
            for (String name : groups)
                users.addAll(customerService.findUsersInGroup(name));
        }
        return users.parallelStream().filter(u -> !u.equals("admin")).distinct().collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/lockedUser")
    @Transactional
    public Map<String, Object> lockedUser(@RequestParam("uName") String uName) {
        Map<String, Object> model = new HashMap<>();
        Assert.hasText(uName, "用户名不能为空");
        if (uName.equals("admin")) {
            model.put("id", "LE500");
            model.put("content", "超级管理员不能禁止登陆！");
            model.put("status", "200");
            return model;
        }
        UserDetails user = customerService.loadUserByUsername(uName);
        if (user != null) {
            UserDetails newUser = new User(uName, user.getPassword(), !user.isEnabled(), true, true, true, user.getAuthorities());
            customerService.updateUser(newUser);
            model.put("id", "LE200");
            if (user.isEnabled()) {
                model.put("content", "用户禁止登陆成功！");
            } else {
                model.put("content", "用户启用登陆成功！");
            }
            model.put("status", "200");
        }
        return model;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/deleteUser")
    @Transactional
    public Map<String, Object> deleteUser(@RequestParam("uName") String uName) {
        Map<String, Object> model = new HashMap<>();
        Assert.hasText(uName, "用户名不能为空");
        if (uName.equals("admin")) {
            model.put("id", "LE500");
            model.put("content", "超级管理员不能删除！");
            model.put("status", "200");
            return model;
        }
        customerService.deleteCustomer(uName);
        customerService.deleteUser(uName);
        model.put("id", "LE200");
        model.put("content", "用户删除成功");
        model.put("status", "200");
        return model;

    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/defaultPassword")
    public Map<String, Object> restUserPwd(@RequestParam("uName") String uName) {
        Map<String, Object> model = new HashMap<>();
        Assert.hasText(uName, "用户名不能为空");
        UserDetails user = customerService.loadUserByUsername(uName);
        if (user != null) {
            User newuser = new User(uName, bCryptEncoder.encode("123456"), user.getAuthorities());
            customerService.updateUser(newuser);
            Customer ctm = customerService.loadCustomerByUsername(uName);
            List<String> phoneList = new ArrayList<>();
            if (ctm != null && ctm.getPhone() != null) {
                phoneList.add(ctm.getPhone());
                smsHelper.runTaskSend("你的绿·硒邮寄登陆名为:" + uName + "的密码已被重置为123456，请牢记密码，重新登陆系统！");
            }

        }
        model.put("id", "LE200");
        model.put("content", "用户密码重置成功！");
        model.put("status", "200");
        return model;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/loadUserByName")
    public UserDetails loadUserByName(@RequestParam("uName") String username) {
        return customerService.loadUserByUsername(username);

    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/updateuserroles")
    public Map<String, Object> updateUserRole(@RequestParam(value = "username") String userName,
                                              @RequestParam(value = "authorities[]") List<String> authorities) {
        Map<String, Object> model = new HashMap<>();
        if (userName.isEmpty() || authorities.size() == 0) {
            model.put("id", "LE500");
            model.put("content", "用户名和权限为空，请重试");
            model.put("status", "500");
            return model;
        }
        UserDetails user = customerService.loadUserByUsername(userName);
        List<GrantedAuthority> AUTHORITIES = new ArrayList<>();
        authorities.forEach(p -> AUTHORITIES.add(new SimpleGrantedAuthority(p)));
        User u = new User(user.getUsername(), user.getPassword(), AUTHORITIES);
        customerService.updateUser(u);
        model.put("id", "LE200");
        model.put("content", "用户权限更新成功！");
        model.put("status", "200");
        return model;
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
    @RequestMapping("/addUser")
    public Map<String, Object> addUser(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "password") String password,
                                       @RequestParam(value = "email") String email,
                                       @RequestParam(value = "phone") String phone,
                                       @RequestParam(value = "ifAdmin") Boolean ifAdmin) {
        Assert.hasText(username, "用户名不能为空");
        Assert.hasText(password, "密码不能为空");
        Assert.hasText(email, "电子邮件不能为空");
        Assert.hasText(phone, "联系电话不能为空");
        Map<String, Object> model = new HashMap<>();

        List<GrantedAuthority> authorities = new ArrayList<>();

        Customer customer = new Customer(username, bCryptEncoder.encode(password), phone, email);
        customerService.saveCustomer(customer);

        if (ifAdmin)//管理员
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        User user = new User(username, bCryptEncoder.encode(password), authorities);

        customerService.updateUser(user);

        if (ifAdmin)
            customerService.addUserToGroup(username, "administrators");

        customerService.addUserToGroup(username, "users");

        model.put("id", "LE200");
        model.put("content", "账户创建成功!密码：" + password + ",请牢记！");
        model.put("status", "200");

        return model;
    }

    @RequestMapping("/updateUser")
    public Map<String, Object> updateCustomer(@RequestBody Customer customer) {
        Assert.hasText(customer.getConsignee(), "收货人不能为空");
        Assert.hasText(customer.getEmail(), "电子邮件不能为空");
        Assert.hasText(customer.getPhone(), "联系电话不能为空");
        customerService.saveCustomer(customer);
        Map<String, Object> model = new HashMap<>();
        model.put("id", "LE200");
        model.put("content", "更新信息成功！");
        model.put("status", "200");

        return model;
    }

    @RequestMapping("/changpwd")
    public Map<String, Object> changPassword(@RequestParam(value = "oldpassword") String oldpassword,
                                             @RequestParam(value = "password") String password) {
        Map<String, Object> model = new HashMap<>();
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            model.put("id", "LE500");
            model.put("content", "系统认证错误，请重新登陆！");
            model.put("status", "500");
            return model;
        }
        String username = currentUser.getName();
        UserDetails user = customerService.loadUserByUsername(username);
        if (!bCryptEncoder.matches(oldpassword, user.getPassword())) {
            model.put("id", "LE501");
            model.put("content", "原始登陆密码错误,请重试.");
            model.put("status", "500");
            return model;
        }
        customerService.changePassword(bCryptEncoder.encode(oldpassword), bCryptEncoder.encode(password));
        SecurityContextHolder.clearContext();
        model.put("id", "LE200");
        model.put("content", "密码修改成功!");
        model.put("status", "200");
        return model;
    }

}
