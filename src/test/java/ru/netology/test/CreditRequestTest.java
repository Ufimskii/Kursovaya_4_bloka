package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;
import ru.netology.page.OrderPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.netology.data.DataHelper.getPropertyOrDefValue;

public class CreditRequestTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open(getPropertyOrDefValue("service.url", "http://localhost:8080/"));
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @Description("Успешная покупка в кредит")
    void shouldCreditApprovedCard() {
        val cardInfo = new DataHelper().getValidCardInfo("approved");
        val creditPage = new OrderPage().goToCredit();
        creditPage.credit(cardInfo);
        creditPage.approved();
        assertEquals("APPROVED", new DbHelper().getCreditRequestStatus());
        assertNull(new DbHelper().getCreditId());
    }

    @Test
    @Description("Попытка покупки в кредит по карте с недостаточным балансом")
    void shouldPaymentDeclinedCard() {
        val cardInfo = new DataHelper().getValidCardInfo("declined");
        val creditPage = new OrderPage().goToCredit();
        creditPage.credit(cardInfo);
        creditPage.declined();
        assertEquals("DECLINED", new DbHelper().getCreditRequestStatus());
        assertNull(new DbHelper().getCreditId());
    }

    @Test
    @Description("Попытка покупки в кредит с невалидными данными #1")
    void shouldGetNotificationInvalidCard() {
        val cardInfo = new DataHelper().getInvalidCardInfo("approved");
        val creditPage = new OrderPage().goToCredit();
        creditPage.credit(cardInfo);
        creditPage.invalidCardNotification();
    }

    @Test
    @Description("Попытка покупки в кредит с невалидными данными #2")
    void shouldGetNotificationWrongFormatCard() {
        val cardInfo = new DataHelper().getInvalidFormatCardInfo("4444");
        val creditPage = new OrderPage().goToCredit();
        creditPage.credit(cardInfo);
        creditPage.wrongFormatNotification();
    }

    @Test
    @Description("Попытка покупки в кредит с незаполненной формой")
    void shouldGetNotificationEmptyOrWrongFormatFields() {
        val creditPage = new OrderPage().goToCredit();
        creditPage.emptyFieldNotification();
    }
}
