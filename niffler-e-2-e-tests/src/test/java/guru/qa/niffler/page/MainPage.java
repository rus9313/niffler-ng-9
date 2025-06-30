package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
  private final ElementsCollection tableRows = $("#spendings tbody").$$("tr");
  private final SelenideElement statistics = $x("//h2[text()='Statistics']");
  private final SelenideElement historyOfSpendings = $x("//h2[text()='History of Spendings']");
  private final SelenideElement profileBtn = $("button div.MuiAvatar-root");
  private final SelenideElement profileMenuItem = $("a[href='/profile']");

  public EditSpendingPage editSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription)).$$("td").get(5).click();
    return new EditSpendingPage();
  }

  public void checkThatTableContainsSpending(String spendingDescription) {
    tableRows.find(text(spendingDescription)).should(visible);
  }

  public void checkFieldsAtPage() {
    statistics.should(visible);
    historyOfSpendings.should(visible);
  }

  public ProfilePage openProfilePage() {
    profileBtn.click();
    profileMenuItem.click();
    return new ProfilePage();
  }
}
