package com.core.backend.usergroup.infra;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.core.backend.RepositoryTestEnvSupport;
import com.core.backend.config.AuditingConfig;
import com.core.backend.group.domain.Group;
import com.core.backend.group.infra.JpaGroupRepository;
import com.core.backend.user.domain.User;
import com.core.backend.user.infra.database.JpaUserRepository;
import com.core.backend.usergroup.domain.UserGroup;
import com.core.backend.usergroup.domain.infra.JpaUserGroupRepository;

class JpaUserGroupRepositoryTest extends RepositoryTestEnvSupport {

	@Autowired
	private JpaUserRepository userRepository;

	@Autowired
	private JpaGroupRepository groupRepository;

	@Autowired
	private JpaUserGroupRepository userGroupRepository;

	@Test
	@DisplayName("같은 userId를 가진 Group를 UserGroup로부터 조회한다.")
	void findAllUserGroupByUserIdTest() {
		// given
		User user = User.of("test@email.com", "password", "test", "01011111111");
		userRepository.save(user);

		Group group1 = setGroupData("testGroup1", user);
		Group group2 = setGroupData("testGroup2", user);
		Group group3 = setGroupData("testGroup3", user);
		Group group4 = setGroupData("testGroup4", user);
		Group group5 = setGroupData("testGroup5", user);

		groupRepository.saveAll(List.of(group1, group2, group3, group4, group5));

		// when
		List<UserGroup> userGroups = userGroupRepository.findAllByUserId(user.getId());

		// then
		assertThat(userGroups).hasSize(5);
		assertThat(userGroups)
			.extracting(
				userGroup -> userGroup.getGroup().getGroupName(),
				userGroup -> userGroup.getUser().getEmail()
			)
			.containsExactlyInAnyOrder(
				tuple("testGroup1", "test@email.com"),
				tuple("testGroup2", "test@email.com"),
				tuple("testGroup3", "test@email.com"),
				tuple("testGroup4", "test@email.com"),
				tuple("testGroup5", "test@email.com")
			);
	}

	private Group setGroupData(String testGroup1, User user) {
		Group group = Group.of(testGroup1);
		group.addUserToGroup(user);

		return group;
	}
}
