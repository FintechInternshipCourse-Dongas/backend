package com.core.backend.group.application;import java.util.List;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.core.backend.auth.ui.dto.AuthUser;import com.core.backend.group.domain.repository.GroupRepository;import com.core.backend.group.ui.dto.GroupInfoResponse;import lombok.RequiredArgsConstructor;import lombok.extern.slf4j.Slf4j;@Slf4j@Service@Transactional@RequiredArgsConstructorpublic class GroupQueryService {	private final GroupRepository groupRepository;	public List<GroupInfoResponse> getAllGroup(AuthUser authUser) {		return null;	}}