# 65apps

Домашнее задание №1 - Создан проект с пустой activity.

Домашнее задание №2 - Созданы 1 activity и 2 фрагмента: "Список контактов" и "Детали контакта".

Домашнее задание №3 - Создан привязанный сервис с асинхронными методами для получения данных контактов.

Домашнее задание №4 - У контакта создано дополнительное поле "День рождения" с кнопкой для включения и выключения уведомлений о нем с использованием Alarm Manager, Broadcast Receiver и выводом уведомлений на экран через Notification, позволяющих открыть детальную карточку контакта, у которого сегодня день рождения.

Домашнее задание №5 - Добавлена обработка запроса разрешений (permissions), в методе загрузки списка контактов реализовано получение списка всех контактов пользователя из поставщика контактов, в методе загрузки деталей контакта по ID - реализовано получение всех необходимых данных контакта из поставщика контактов по ID.

Домашнее задание №6 - Произведен рефакторинг на MVVM. Контакты грузятся из поставщика контактов через репозиторий, обращение к привязанному сервису убрано.

Домашнее задание №7 - Подключен RecyclerView с ListAdapter. Реализован поиск контактов по именам с использованием SearchView, внедренного в Toolbar. К RecyclerView добавлено отображение разделителей элементов списка через собственную реализацию ItemDecoration

Домашнее задание №8 - Произведен рефакторинг на RxJava. Добавлена индикация загрузки данных.

Домашнее задание №9 - Произведен рефакторинг на Dagger 2, включая внедрение вьюмоделей, использование различных scope, организацию зависимостей через subcomponents.

Домашнее задание №10 - Произведен рефакторинг на Clean Architecture с разбивкой приложения на 3 gradle-модуля.

Курсовая работа.

Домашнее задание №11 - Написаны модульные и интеграционные тесты для проверки операций с напоминаниями о днях рождения.

Домашнее задание №12 - Внедрен статический анализ кода detekt и ktlint.