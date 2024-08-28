# Проект по автоматизации тестирования для компании [<img width="35%" title="Trello" src="media/logo/Trello-logo.png"/>](https://trello.com/)
> Trello — облачная программа для управления проектами небольших групп, разработанная Fog Creek Software. 
---

## Содержание:
+ [Описание проекта и используемый стек](#описание-проекта-и-используемый-стек)
+ [Реализованные проверки](#реализованные-проверки)
+ [Сборка в Jenkins](#-сборка-в-jenkins)
+ [Интеграция с Allure Report](#-интеграция-с-allure-report)
+ [Интеграция с Allure TestOps](#-интеграция-с-allure-testops)
+ [Уведомления в Telegram с использованием бота](#-уведомления-в-telegram-с-использованием-бота)
+ [Видео примера запуска тестов в Selenoid](#-видео-примера-запуска-тестов-в-selenoid)
---

## Описание проекта и используемый стек
<p align="center">
<a href="https://www.jetbrains.com/idea/"><img width="6%" title="IntelliJ IDEA" src="media/logo/Intelij_IDEA.svg"/></a> 
<a href="https://www.java.com/"><img width="6%" title="Java" src="media/logo/Java.svg"/></a>
<a href="https://gradle.org/"><img width="6%" title="Gradle" src="media/logo/Gradle.svg"/></a> 
<a href="https://selenide.org/"><img width="6%" title="Selenide" src="media/logo/Selenide.svg"/></a> 
<a href="https://aerokube.com/selenoid/"><img width="6%" title="Selenoid" src="media/logo/Selenoid.svg"/></a>
<a href="https://rest-assured.io"><img width="6%" title="REST-Assured" src="media/logo/RestAssured.svg"/></a>
<a href="https://github.com/allure-framework/allure2"><img width="6%" title="Allure Report" src="media/logo/Allure_Report.svg"/></a>
<a href="https://qameta.io"><img width="6%" title="Allure TestOps" src="media/logo/Allure_TO.svg"/></a>
<a href="https://junit.org/junit5/"><img width="6%" title="JUnit5" src="media/logo/JUnit5.svg"/></a> 
<a href="https://github.com/"><img width="6%" title="GitHub" src="media/logo/GitHub.svg"/></a> 
<a href="https://www.jenkins.io/"><img width="6%" title="Jenkins" src="media/logo/Jenkins.svg"/></a> 
<a href="https://telegram.org/"><img width="6%" title="Telegram" src="media/logo/Telegram.svg"/></a>  
<a href="https://app-automate.browserstack.com/"><img width="6%" title="BrowserStack" src="media/logo/Browserstack.svg"/></a>  
<a href="https://developer.android.com/studio"><img width="6%" title="Android Studio.svg" src="media/logo/Android_Studio.svg"/></a>   
<a href="https://appium.io"><img width="6%" title="Appium" src="media/logo/Appium.svg"/></a>  
<a href="https://www.atlassian.com/software/jira"><img width="6%" title="Jira" src="media/logo/Jira.svg"/></a>  
</p>   

- Проект состоит из UI-тестов, API и мобильных тестов для Android
- В данном проекте автотесты написаны на языке `Java`
- В качестве сборщика используется `Gradle`
- Используются фреймворки `JUnit 5` и `Selenide`
- Используется технология `Owner` для придания тестам гибкости и легкости конфигурации
- Используется `Lombok` для моделей в API тестах
- При прогоне UI тестов браузер запускается в `Selenoid`
- Для запуска мобильных тестов удаленно используется `Browserstack`
- Для запуска мобильных тестов локально на эмуляторе или реальном устройстве используются `Appium` и `Android Studio`
- Реализована возможность запуска проектов с помощью `Jenkins`
- Реализована интеграция с `Allure TestOps`
- Настроена отправка уведомлений о результатах прохождения в `Telegram`
- По завершению прохождения автотестов генерируется `Allure` отчет
---

## Реализованные проверки:

### Web
- [x] Проверка формы создания доски
- [x] Проверка создания доски c типом видимости: рабочее пространство
- [x] Проверка редактирования названия доски c типом видимости: рабочее пространство
- [x] Проверка удаления доски c типом видимости: рабочее пространство

### Api
- [x] Проверка создания, редактирования, удаления доски через API

### Mobile
- [x] Проверка авторизации в приложении Trello
---

## <img width="4%" style="vertical-align:middle" title="Jenkins" src="media/logo/Jenkins.svg"> Сборка в [Jenkins](https://jenkins.autotests.cloud/job/C27-girlonthemars-HW23-LeaderTask-Tests-Project/)

* <code>TASK</code> – название запускаемой задачи. По умолчанию - <code>main_web</code>.
* <code>BROWSER</code> – браузер, в котором будут выполняться тесты. По умолчанию - <code>chrome</code>.
* <code>BROWSER_VERSION</code> – версия браузера, в которой будут выполняться тесты. По умолчанию - <code>-</code>.
* <code>BROWSER_SIZE</code> – размер окна браузера, в котором будут выполняться тесты. По умолчанию - <code>1920x1080</code>.
* <code>Wdhost</code> – адрес selenoid для удаленного запуска тестов. 
* <code>ApiKey</code> – ключ для доступа к Api. 
* <code>ApiToken</code> – токен для доступа к Api.
  
***Параметры запуска:***
```
clean
${TEST_TASK}
-DBrowser=${BROWSER}
-DChromeVersion=${CHROME_VERSION}
-DFirefoxVersion=${FIREFOX_VERSION}
-DBrowserSize=${BROWSER_SIZE}
-DWdhost=${WDHOST}
-Dapikey=${ApiKey}
-Dapitoken=${ApiToken}
```
Для запуска сборки необходимо перейти в раздел <code>Собрать с параметрами</code>, задать параметры и нажать кнопку <code>Собрать</code>.

<p align="center">
<img title="Jenkins" src="media/screenshots/JParameters1.png"> 
<img title="Jenkins" src="media/screenshots/JParameters2.png">
</p>

После выполнения сборки, в блоке <code>История сборок</code> напротив номера сборки появится значок <code>Allure Report</code>, при клике на который открывается страница со сформированным html-отчетом,
а так же значок <code>Allure TestOps</code> при клике на который открывается страница проекта в <code>Allure TestOps</code>.

<p align="center">
<img title="Jenkins" src="media/screenshots/JBuild.png">
</p>

---
## <img width="4%" style="vertical-align:middle" title="Allure_Report" src="media/logo/Allure_Report.svg"> Интеграция с [Allure Report](https://jenkins.autotests.cloud/job/C27-girlonthemars-HW23-LeaderTask-Tests-Project/allure/)
`Allure Report` - инструмент для генерации и визуализации отчетов о выполнении тестов, который позволяет представлять результаты тестирования в наглядной и удобной форме.

### Диаграмма прохождения тестов

<p align="center">  
<img title="Allure Overview Dashboard" src="media/screenshots/AMain.png">  
</p>  

### Развернутый результат прохождения тестов

<p align="center">  
<img title="Allure Tests" src="media/screenshots/ATestCases.png">  
</p>

---
## <img width="4%" style="vertical-align:middle" title="Allure_TestOps" src="media/logo/Allure_TO.svg"> Интеграция с [Allure TestOps](https://allure.autotests.cloud/project/4369/dashboards)
`Allure TestOps` - платформа для управления тестированием, которая предоставляет обширный набор для организации, выполнения и анализа тестов в проектах.

<p align="center">  
<img title="Allure TestOps Dashboard" src="media/screenshots/TOTestCases.png">  
</p>

---
## <img width="4%" style="vertical-align:middle" title="Telegram" src="media/logo/Telegram.svg"> Уведомления в Telegram с использованием бота

После завершения сборки специальный бот автоматически отправляет сообщение с отчетом о прогоне тестов в `Telegram`.

<p align="center">
<img width="50%" title="Notifications" src="media/screenshots/Telegram.png">
</p>

---
## <img width="4%" style="vertical-align:middle" title="Selenoid" src="media/logo/Selenoid.svg"> Видео примера запуска тестов в Selenoid

В отчетах Allure для каждого теста прикреплен не только скриншот, но и видео прохождения теста.
<p align="center">
  <img title="Selenoid Video" src="media/screenshots/30b36741ba7252964adfa1ce7d41c6ce.gif">
</p>
