package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms/");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");

		WebElement password = webDriver.findElement(By.id("password"));
		password.clear();
		password.sendKeys("StudentAA01");

		WebElement loginBtn = webDriver.findElement(By.className("btn"));
		loginBtn.click();

		pageLoadTimeout(10);
		assertEquals("セキュリティ規約 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		WebElement checkbox = webDriver.findElement(By.cssSelector("input[name=\"securityFlg\"]"));
		checkbox.click();

		WebElement nextBtn = webDriver.findElement(By.cssSelector("#main button[type=\"submit\"]"));
		nextBtn.click();

		pageLoadTimeout(10);
		assertEquals("パスワード変更 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA01");

		WebElement newPassword = webDriver.findElement(By.id("password"));
		newPassword.clear();
		newPassword.sendKeys("Password123");

		WebElement confirmPassword = webDriver.findElement(By.id("passwordConfirm"));
		confirmPassword.clear();
		confirmPassword.sendKeys("Password123");

		WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type=\"submit\"]"));
		scrollTo(String.valueOf(submitBtn.getLocation().getY()));
		submitBtn.click();

		visibilityTimeout(By.id("div-modal"), 10);
		WebElement updateBtn = webDriver.findElement(By.id("upd-btn"));
		updateBtn.click();

		pageLoadTimeout(10);
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

}
