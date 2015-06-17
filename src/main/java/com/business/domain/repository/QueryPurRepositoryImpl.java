package com.business.domain.repository;

import com.business.domain.QueryPur;
import com.business.domain.QueryPurItem;
import com.business.domain.QueryPurStatistic;
import org.hibernate.action.internal.CollectionRecreateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by billb on 2015/6/3.
 */
public class QueryPurRepositoryImpl implements QueryPurRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    public QueryPurRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<QueryPurStatistic> findAllGroupBy(Map<String, Object> params,Pageable pageable) {
        String countHql = "select count(*) from QueryPurItem a inner join a.queryPur b "+specToParams(params)+" group by a.goodsid,a.name,a.price";
        Query countQuery = entityManager.createQuery(countHql);
        specSetParameter(params,countQuery);
        long total = countQuery.getResultList().size();
        if (total == 0) {
            return new PageImpl<>(new ArrayList<>(), pageable, total);
        }

        String hql = "select new com.business.domain.QueryPurStatistic(a.goodsid,a.name,a.price,sum(a.qty),sum(a.valqty),sum(a.retqty))" +
                " from QueryPurItem a inner join a.queryPur b "+specToParams(params)+" group by a.goodsid,a.name,a.price";
        Query q = entityManager.createQuery(hql);

        specSetParameter(params,q);
        q.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(q.getResultList(), pageable, total);
    }


    private String specToParams(Map<String, Object> params) {
            StringBuilder sb = new StringBuilder();
            sb.append("where ");
            String gName = params.containsKey("gName") && params.get("gName") != null ? params.get("gName").toString() : null;
            int flag = params.containsKey("flag") && params.get("flag") != null ? Integer.parseInt(params.get("flag").toString()) : 0;
            Object[] gid = (Object[]) params.get("gId");

            if (params.get("start") != null && params.get("end") != null)
                sb.append("b.checkdate between :start and :end");
            if (flag > 0)
                sb.append(" and b.flag = :flag");

            if (gid != null) {
                if (gid.length > 0) {
                    sb.append(" and a.goodsid in :gid");
                }
            }
            if (gName != null && gName.length()>0) {
                sb.append("and a.name like %:name%");
            }
        return sb.toString();
    }

    private void specSetParameter(Map<String, Object> params,Query query) {

        String gName = params.containsKey("gName") && params.get("gName") != null ? params.get("gName").toString() : null;
        Integer flag = params.containsKey("flag") && params.get("flag") != null ? Integer.parseInt(params.get("flag").toString()) : 0;
        Object[] gid = (Object[]) params.get("gId");

        if (params.get("start") != null && params.get("end") != null)
            query.setParameter("start", Timestamp.valueOf(params.get("start").toString()), TemporalType.TIMESTAMP);
            query.setParameter("end", Timestamp.valueOf(params.get("end").toString()), TemporalType.TIMESTAMP);
        if (flag > 0)
            query.setParameter("flag",flag);
        if (gid != null) {
            if (gid.length > 0) {
                List<Integer> ids = new ArrayList<>();
                for(Object a : gid){
                        ids.add(Integer.parseInt(a.toString()));
                }
                query.setParameter("gid",ids);
            }
        }
        if (gName != null && gName.length()>0) {
            query.setParameter("name", gName);
        }
    }
}
