package com.core.backend.usergroup.domain.infra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.core.backend.usergroup.domain.UserGroup;

public interface JpaUserGroupRepository extends JpaRepository<UserGroup, Long> {

	List<UserGroup> findAllByUserId(final Long userId);
}
