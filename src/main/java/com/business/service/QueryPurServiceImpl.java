package com.business.service;

import com.business.domain.QueryPur;
import com.business.domain.QueryPurItem;
import com.business.domain.QueryPurStatistic;
import com.business.domain.repository.OrderGoodsRepository;
import com.business.domain.repository.QueryPurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by billb on 2015-05-12.
 */
@Service
public class QueryPurServiceImpl implements QueryPurService {

    private QueryPurRepository purRepos;
    private OrderGoodsRepository orderGoodsRepository;

    @Autowired
    public QueryPurServiceImpl(QueryPurRepository purReposImpl, OrderGoodsRepository orderGoodsRepository) {
        this.purRepos = purReposImpl;
        this.orderGoodsRepository = orderGoodsRepository;
    }

    public List<QueryPurItem> findAllPurToday(Map<String, Object> params) {
        return purRepos.findAll(specToParams(params));
    }


    public Page<QueryPurItem> findAllParams(Map<String, Object> params, Pageable page) {

        return purRepos.findAll(specToParams(initParams(params)), page);
    }

    public Page<QueryPurStatistic> findPurParamsGroupBy(Map<String, Object> params, Pageable page) {

        return purRepos.findAllGroupBy(initParams(params), page);
    }


    private Specification<QueryPurItem> specToParams(Map<String, Object> params) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String uName = params.containsKey("uName") && params.get("uName") != null ? params.get("uName").toString() : null;
            String gName = params.containsKey("gName") && params.get("gName") != null ? params.get("gName").toString() : null;
            int flag = params.containsKey("flag") && params.get("flag") != null ? Integer.parseInt(params.get("flag").toString()) : 0;
            Object[] gid = (Object[]) params.get("gId");

            Join<QueryPurItem, QueryPur> depJoin = root.join(root.getModel().getSingularAttribute("queryPur", QueryPur.class), JoinType.INNER);

            if (params.get("start") != null && params.get("end") != null)
                predicates.add(builder.between(depJoin.get("checkdate").as(Timestamp.class),
                        Timestamp.valueOf(params.get("start").toString()),
                        Timestamp.valueOf(params.get("end").toString())));
            if (uName != null && uName.length() > 0)
                predicates.add(builder.equal(depJoin.get("username").as(String.class), uName));
            if (flag > 0)
                predicates.add(builder.equal(depJoin.get("flag").as(Integer.class), flag));
            if (gid != null) {
                Expression<Integer> exp = root.get("goodsid");
                if (gid.length > 0) {
                    predicates.add(exp.in(gid));
                }
            }
            if (gName != null && gName.length() > 0) {
                predicates.add(builder.like(root.get("name").as(String.class), "%" + gName + "%"));
            }
            Predicate[] p = new Predicate[predicates.size()];
            return builder.and(predicates.toArray(p));
        };
    }

    private Map<String, Object> initParams(Map<String, Object> params) {
        int deptId = params.get("deptId") != null ? Integer.parseInt(params.get("deptId").toString()) : 0;
        String gParam = params.get("gParam") != null ? params.get("gParam").toString() : null;
        if (deptId > 0) {
            List<Integer> goodsIds = new ArrayList<>();
            orderGoodsRepository.findByParamDeptId(deptId).parallelStream().forEach(p -> goodsIds.add(p.getGId()));
            if (goodsIds.size() == 0)
                goodsIds.add(-1);
            params.put("gId", goodsIds.toArray());
        }
        if (gParam != null && gParam.length() > 0) {
            try {
                Integer[] gId = {Integer.parseInt(gParam)};
                params.put("gId", gId);
            } catch (NumberFormatException e) {
                params.put("gName", gParam);
            }
        }
        return params;
    }

}
