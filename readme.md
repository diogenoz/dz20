# Необходимые ресурсы:

## Необходимо скачать:
Apache Maven: https://maven.apache.org/download.cgi
GWT SDK:
http://www.gwtproject.org/download.html

## Создание проекта через webAppCreator с поддержкой Apache Maven:
C:\GWTProject>C:/gwt-2.8.2/webAppCreator -templates maven,sample -out WebApp com.flamexander.webapp.WebApp

## Запуск приложения:
mvn clean war:exploded gwt:devmode

## Не забудьте сделать (псевдокод)common:install

## Документация:

#### Основная часть:
- http://www.gwtproject.org/doc/latest/DevGuide.html
#### Элементы управления:
- http://samples.gwtproject.org/samples/Showcase/Showcase.html#!CwCheckBox
#### Описание Maven плагина
- https://tbroyer.github.io/gwt-maven-plugin/
#### GWT Materials
- https://gwtmaterialdesign.github.io/gwt-material-demo/2.0-SNAPSHOT/#!gettingstarted

##### Домашнее задание:
1. Вывести в таблицу список всех задач
2. Добавить фильтр в виде формы
*. Если не получилось во всем сразу разобраться: в качестве дз
сдаете возникшие вопросы