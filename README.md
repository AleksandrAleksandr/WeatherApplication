# WeatherApplication
## Приложение для прогноза погоды
### Функционал 
#### Имеется список городов, у города можно посмотреть прогноз погоды на 5 дней. Есть возможность добавлять города, используя поиск. Добавленные города локально хранятся в БД. Для "домашнего" города  регулярно (примерно 2 раза в день) приходят уведомления с ближайшим прогнозом.
#### Использованные технологии

+ Retrofit
+ паттерн Repository
+ Android Architecture Components
  + ViewModel
  + LiveData
  + Room
  + WorkManager для создания уведомлений 
  + Navigation
  + Hilt (Dependency Injection)

![Альтернативный текст](screenshots/1.jpg) &nbsp;&nbsp;  ![Альтернативный текст](screenshots/2.jpg) <br />
![Альтернативный текст](screenshots/3.jpg) &nbsp;&nbsp;  ![Альтернативный текст](screenshots/4.jpg) &nbsp;&nbsp;
![Альтернативный текст](screenshots/5.jpg) <br />  
#### Пример уведомления
![Альтернативный текст](screenshots/6.jpg) 

 
