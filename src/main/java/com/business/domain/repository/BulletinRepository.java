package com.business.domain.repository;

import com.business.domain.Bulletin;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by billb on 2015-04-21.
 */
@RepositoryRestResource(path = "bulletin")
@PreAuthorize("hasRole('ROLE_ADMINISTRATORS')")
public interface BulletinRepository extends PagingAndSortingRepository<Bulletin, Long> {

}
