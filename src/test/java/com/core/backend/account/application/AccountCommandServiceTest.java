package com.core.backend.account.application;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.core.backend.account.AccountServiceTestFixture;
import com.core.backend.account.application.dto.AccountRegisterServiceRequest;
import com.core.backend.account.domain.Account;
import com.core.backend.account.exception.AccountException;
import com.core.backend.user.domain.User;

class AccountCommandServiceTest extends AccountServiceTestFixture {

	@Test
	@Transactional
	@DisplayName("사용자의 결제 계좌를 등록한다.")
	void registerAccountTest() {
		// given
		User user = User.of("test@email.com", "password", "test", "01011111111");
		userRepository.save(user);

		AccountRegisterServiceRequest request = AccountRegisterServiceRequest.ofCreate("국민은행", "testNumber");

		// when
		accountCommandService.registerAccount(user.getId(), request);

		// then
		Account testAccount = accountRepository.findAll().stream()
			.filter(account -> account.getBankCode().getBankName().equals(request.getBankName()))
			.findFirst()
			.orElse(null);

		SoftAssertions.assertSoftly(softAssertions -> {
			Assertions.assertThat(testAccount).isNotNull();
			Assertions.assertThat(testAccount.getUser())
				.extracting("id")
				.isEqualTo(user.getId());
		});
	}

	@Test
	@Transactional
	@DisplayName("중복되는 계좌번호가 존재할 경우 예외가 발생한다.")
	void duplicateAccountNumberException() {
		User user = User.of("test@email.com", "password", "test", "01011111111");
		userRepository.save(user);

		Account account = Account.of("국민은행", "testNumber", user);
		accountRepository.save(account);

		AccountRegisterServiceRequest request = AccountRegisterServiceRequest.ofCreate("국민은행", "testNumber");

	    // when & then
		assertThatThrownBy(() -> accountCommandService.registerAccount(user.getId(), request))
			.isInstanceOf(AccountException.class)
			.hasMessage("중복되는 계좌번호 입니다.");
	}

}
