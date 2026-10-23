package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");

		WebElement password = webDriver.findElement(By.id("password"));
		password.clear();
		password.sendKeys("Password123");

		WebElement loginBtn = webDriver.findElement(By.className("btn"));
		loginBtn.click();

		pageLoadTimeout(10);
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		WebElement dropdownToggle = webDriver.findElement(By.className("dropdown-toggle"));
		dropdownToggle.click();

		WebElement help = webDriver.findElement(By.linkText("ヘルプ"));
		help.click();

		pageLoadTimeout(10);
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		WebElement faq = webDriver.findElement(By.linkText("よくある質問"));
		faq.click();

		pageLoadTimeout(10);

		//よくある質問が別タブで開くため切り替える
		Object[] windowHandles = webDriver.getWindowHandles().toArray();
		webDriver.switchTo().window((String) windowHandles[1]);
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		WebElement keywordForm = webDriver.findElement(By.id("form"));
		keywordForm.sendKeys("営業");

		WebElement submitBtn = webDriver.findElement(By.cssSelector("input[type=\"submit\"]"));
		submitBtn.click();

		visibilityTimeout(By.id("question-h1"), 10);
		WebElement firstFaq = webDriver.findElement(By.id("question-h1"));
		WebElement secondFaq = webDriver.findElement(By.id("question-h2"));
		WebElement thirdFaq = webDriver.findElement(By.id("question-h0"));

		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("Q.研修の申し込みはどのようにすれば良いですか？", firstFaq.getText());
		assertEquals("Q.助成金書類の作成方法が分かりません", secondFaq.getText());
		assertEquals("Q.キャンセル料・途中退校について", thirdFaq.getText());

		getEvidence(new Object() {
		}, "1");
		scrollBy("250");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		WebElement clearBtn = webDriver.findElement(By.cssSelector("input[type=\"button\"]"));
		clearBtn.click();

		WebElement keywordForm = webDriver.findElement(By.id("form"));
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		assertEquals("", keywordForm.getText());

		scrollTo("0");
		getEvidence(new Object() {
		});
	}

}
