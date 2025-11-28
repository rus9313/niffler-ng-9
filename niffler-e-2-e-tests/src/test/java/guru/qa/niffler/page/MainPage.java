package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {
  private final ElementsCollection tableRows = $$("tbody tr");
  private final SelenideElement statistics = $x("//h2[text()='Statistics']");
  private final SelenideElement historyOfSpendings = $x("//h2[text()='History of Spendings']");
  private final SelenideElement profileBtn = $("button div.MuiAvatar-root");
  private final SelenideElement profileMenuItem = $("a[href='/profile']");
  private final SelenideElement friendsItem = $("a[href='/people/friends']");
  private final SelenideElement allPeopleItem = $("a[href='/people/all']");
  private final SelenideElement search = $("input[placeholder='Search']");

  public EditSpendingPage editSpending(String... spendingDescriptions) {
    for(String description: spendingDescriptions) {
      search(description);
      tableRows.find(text(description)).$$("td").get(5).click();
    }
    return new EditSpendingPage();
  }

  public void checkThatTableContainsSpending(String... spendingDescriptions) {
    for(String description: spendingDescriptions) {
      search(description);
      tableRows.find(text(description)).should(visible);
    }
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
  public FriendsPage openFriendsPage() {
    profileBtn.click();
    friendsItem.click();
    return new FriendsPage();
  }

  public FriendsPage openAllPeoplePage() {
    profileBtn.click();
    allPeopleItem.click();
    return new FriendsPage();
  }
  public void search(String name) {
    search.shouldBe(visible).clear();
    search.sendKeys(name);
    search.pressEnter();
  }
}
