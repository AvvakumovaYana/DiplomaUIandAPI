package tests.web;

import helpers.api.TrelloAPI;
import io.qameta.allure.AllureId;
import io.qameta.allure.Owner;
import models.BoardModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.web.BoardCreationForm;
import pages.web.BoardPage;
import pages.web.MainBoardsPage;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Аввакумова Яна")
@Tag("web")

public class WebBoardTest extends TestBase {

    private final MainBoardsPage mainBoardsPage = new MainBoardsPage();
    private final BoardCreationForm boardCreationForm = new BoardCreationForm();
    private final TrelloAPI api = new TrelloAPI();

    @Test
    @AllureId("34133")
    @DisplayName("Проверка формы создания доски")
    void boardCreationFormTest() {
        step("Открываем страницу c досками пользователя", () -> {
            mainBoardsPage.openPage();
        });
        step("Открываем форму создания доски", () -> {
            mainBoardsPage.clickCreateBoardButton();
        });
        step("Проверяем содержание формы создания доски", () -> {
            boardCreationForm
                    .checkFormTitle()
                    .checkBoardPreview()
                    .checkLabelBackground()
                    .checkBackgroundTypes()
                    .checkBoardNameLabel()
                    .checkBoardNameField()
                    .checkSetBoardNameLabel()
                    .checkBoardVisibilityLabel()
                    .checkBoardVisibilityField()
                    .checkCreateButton()
                    .checkTemplateButton()
                    .checkAgreementText()
                    .checkUseTermsLink()
                    .checkLicensesRulesLink()
                    .checkCloseButton();
        });
    }

    @Test
    @AllureId("34132")
    @DisplayName("Проверка создания доски c типом видимости: рабочее пространство")
    void boardCreationTest() {
        BoardPage boardPage = new BoardPage("Board creation test UI");

        step("Открываем страницу c досками пользователя", () -> {
            mainBoardsPage.openPage();
        });
        step("Открываем форму создания доски", () -> {
            mainBoardsPage.clickCreateBoardButton();
        });
        step("Заполняем форму и создаем доску", () -> {
            boardCreationForm
                    .checkFormTitle()
                    .fillBoardNameField(boardPage.getName())
                    .checkBoardVisibilityField()
                    .clickCreateButton();
        });
        String boardId = step("Проверяем форму созданной доски", () -> {
            return boardPage
                    .checkBoardTitle()
                    .checkFirstColumnTitle()
                    .checkSecondColumnTitle()
                    .checkThirdColumnTitle()
                    .getBoardId();
        });
        step("Удаляем доску через API", () -> {
            api.deleteBoard(boardId);
        });
    }

    @Test
    @AllureId("34131")
    @DisplayName("Проверка редактирования названия доски c типом видимости: рабочее пространство")
    void boardUpdateTest() {
        BoardPage boardPage = new BoardPage("Board creation test API");

        BoardModel createResponse = step("Создаем доску через API", () -> {
            return api.createBoard(boardPage.getName());
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createResponse);
        });
        step("Редактируем название доски", () -> {
            boardPage.changeBoardTitle("Board update test UI");
        });
        step("Проверяем, что название доски изменилось", () -> {
            boardPage.checkBoardTitle();
        });
        step("Проверяем изменение названия доски через API", () -> {
            BoardModel getResponse = api.getBoard(boardPage.getBoardId());
            assertThat(getResponse.getName()).isEqualTo(boardPage.getName());
        });
        step("Удаляем доску через API", () -> {
            api.deleteBoard(boardPage.getBoardId());
        });
    }

    @Test
    @AllureId("34134")
    @DisplayName("Проверка удаления доски c типом видимости: рабочее пространство")
    void boardDeleteTest() {
        BoardPage boardPage = new BoardPage("Board delete test UI");

        BoardModel createResponse = step("Создаем доску через API", () -> {
            return api.createBoard(boardPage.getName());
        });
        step("Открываем страницу созданной доски", () -> {
            boardPage.openPage(createResponse);
        });
        step("Удаляем доску через меню", () -> {
            boardPage
                    .openBoardMenu()
                    .clickCloseBoard()
                    .checkCloseBoardFormTitle()
                    .checkCloseBoardFormText()
                    .checkCloseBoardFormLink()
                    .checkFormCloseButton()
                    .clickCloseBoardButton()
                    .clickDeleteBoard()
                    .checkDeleteBoardFormTitle()
                    .checkDeleteBoardFormText()
                    .checkFormCloseButton()
                    .clickDeleteBoardButton();
        });
        step("Проверяем, что отображется страница досок пользователя", () -> {
            mainBoardsPage.checkPageTitle();
        });
        step("Проверяем через API, что доски нет", () -> {
            api.getBoard(boardPage.getBoardId(), 400);
        });
    }
}


