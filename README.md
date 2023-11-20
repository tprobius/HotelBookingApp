# HotelBookingApp

### Тестовое задание на позицию Junior Android разработчик

#### Стек

- Kotlin
- Clean Architecture
- MVVM + UDF
- AdapterDelegates + DiffUtils
- View binding
- Coroutine + Flow
- Cicerone
- Koin
- Room
- Retrofit
- Gson

#### Модули

Приложение разделено на модули:
- app - модуль для di и навигации
- data - модуль для работы с сетью
- features - модуль для экранов приложения
    - hotel - экран с информацией об отеле
    - room - экран выбора номеров
    - booking - экран с деталями о туре и полями для данных путешественников
    - payment - экран-подтверждение заказа
- utils - модуль для вспомогательных классов для recyclerView и viewPager2

#### Описание

<p>
  Приложение позволяет получить данные об отелях и номерах. Выбрав тур и заполнив данные путешественноков, пользователь может забронировать поездку.
</p>

<p>  
    <img src="./screenshots/Screenshot_20231120_144850.png" alt="hotel_screen" width="23%" height="auto">
    <img src="./screenshots/Screenshot_20231120_144910.png" alt="room_data" width="23%" height="auto">
    <img src="./screenshots/Screenshot_20231120_145007.png" alt="booking_screen" width="23%" height="auto">
    <img src="./screenshots/Screenshot_20231120_145024.png" alt="payment_screen" width="23%" height="auto">
</p>

#### Backlog
- Добавить индикацию на ViewPager.
- Навесить на кнопку оплаты валидацию полей ввода данных.
- Добавить кэширование данных.
- Покрыть приложение тестами.
