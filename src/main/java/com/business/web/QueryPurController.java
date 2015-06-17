package com.business.web;

import com.business.domain.QueryPurItem;
import com.business.domain.QueryPurStatistic;
import com.business.service.QueryPurService;
import com.business.service.QueryPurServiceImpl;
import com.business.tools.GoodsStatisticBuildExcel;
import com.business.tools.OrderBuildExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by billb on 2015-05-12.
 */
@RestController
@RequestMapping("queryPur")
public class QueryPurController {

    private QueryPurService queryPurService;

    @Autowired
    public QueryPurController(QueryPurServiceImpl queryPurService) {
        this.queryPurService = queryPurService;
    }

    @RequestMapping("/findPur")
    public HttpEntity<PagedResources<QueryPurItem>> findAll(@RequestBody Map<String, Object> params
            , Pageable pageable
            , PagedResourcesAssembler assembler) {
        Page<QueryPurItem> persons = queryPurService.findAllParams(params, pageable);
        return new ResponseEntity<>(assembler.toResource(persons), HttpStatus.OK);
    }

    @RequestMapping("/findPurGroupBy")
    public HttpEntity<PagedResources<QueryPurStatistic>> findAllGroupBy(@RequestBody Map<String, Object> params,
                                                                        Pageable pageable,
                                                                        PagedResourcesAssembler assembler) {
        Page<QueryPurStatistic> persons = queryPurService.findPurParamsGroupBy(params, pageable);
        return new ResponseEntity<>(assembler.toResource(persons), HttpStatus.OK);
    }

    @RequestMapping(value = "/DownloadExcelPur")
    public ModelAndView exportExcel(@RequestParam(value = "start", required = false) Object start,
                                    @RequestParam(value = "end", required = false) Object end,
                                    @RequestParam(value = "flag", required = false) Object flag,
                                    @RequestParam(value = "deptId", required = false) Object deptId,
                                    @RequestParam(value = "gParam", required = false) Object gParam
            , Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("end", end);
        params.put("flag", flag);
        params.put("deptId", deptId);
        params.put("gParam", gParam);
        Page<QueryPurItem> persons = queryPurService.findAllParams(params, pageable);
        OrderBuildExcel buildExcel = new OrderBuildExcel(persons.getContent());
        ModelAndView modelAndView = new ModelAndView(buildExcel);
        return modelAndView;
    }

    @RequestMapping(value = "/DownloadExcelGoodsSTT")
    public ModelAndView exportSTTGoodsExcel(@RequestParam(value = "start", required = false) Object start,
                                            @RequestParam(value = "end", required = false) Object end,
                                            @RequestParam(value = "flag", required = false) Object flag,
                                            @RequestParam(value = "deptId", required = false) Object deptId,
                                            @RequestParam(value = "gParam", required = false) Object gParam
            , Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        params.put("gParam", gParam);
        params.put("start", start);
        params.put("end", end);
        params.put("flag", flag);
        params.put("deptId", deptId);
        Page<QueryPurStatistic> persons = queryPurService.findPurParamsGroupBy(params, pageable);
        GoodsStatisticBuildExcel buildExcel = new GoodsStatisticBuildExcel(persons.getContent());
        ModelAndView modelAndView = new ModelAndView(buildExcel);
        return modelAndView;
    }


}
