# Проверка вариантов работы со временем

Цель проекта проверка работы с временем в различных часовых поясах на фронте, бэке (Kotlin/Spring) и базе данных (
PostgreSQL).

В Kotlin/Java проверяются типы данных:
* Instant,     
* OffsetDateTime
* LocalTime,   
* OffsetTime

## База данных

Создаём базу данных `timecheck` и таблицу для хранения времени:

```postgresql
CREATE DATABASE timecheck
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

ALTER DATABASE timecheck
    SET "TimeZone" TO 'Europe/Moscow';

CREATE TABLE times
(
    id          varchar(40) PRIMARY KEY,
    instant_dt  timestamptz,
    offset_dt   timestamptz,
    local_time  time,
    offset_time timetz
)
```

## Время на фронте

Стандартная функция toISOString() приводит время к UTC:

```javascript
d = new Date()
d.toISOString() // '2023-10-06T08:15:02.510Z'
```

Для приведения к локальному часовому поясу можно использовать явное преобразование:

```javascript
function toLocalIsoString(date) {
    var tzo = -date.getTimezoneOffset(),
        dif = tzo >= 0 ? '+' : '-',
        pad = function(num) {
            return (num < 10 ? '0' : '') + num;
        };

    return date.getFullYear() +
        '-' + pad(date.getMonth() + 1) +
        '-' + pad(date.getDate()) +
        'T' + pad(date.getHours()) +
        ':' + pad(date.getMinutes()) +
        ':' + pad(date.getSeconds()) +
        dif + pad(Math.floor(Math.abs(tzo) / 60)) +
        ':' + pad(Math.abs(tzo) % 60);
}


toLocalIsoString(d) // '2023-10-06T11:15:02+03:00'
```

## Время на бэке

Запускаем приложение

Проверяем для Московской временной зоны:

```bash
curl --location 'http://localhost:9999/save' \
                                      --header 'Content-Type: application/json' \
                                      --data '{
                                      "id": "MSK",
                                      "instantDt": "2023-10-06T10:02:31+03:00",
                                      "offsetDt": "2023-10-06T10:02:31+03:00",
                                      "localTime": "10:02:31",
                                      "offsetTime": "10:02:31+03:00"
                                  }' | jq

{
  "id": "MSK",
  "instantDt": "2023-10-06T07:02:31Z",
  "offsetDt": "2023-10-06T07:02:31Z",
  "localTime": "10:02:31",
  "offsetTime": "10:02:31+03:00"
}
```

Проверяем для Новосибирской временной зоны:

```bash
curl --location 'http://localhost:9999/save' \
                             --header 'Content-Type: application/json' \
                             --data '{
                             "id": "NSK",
                             "instantDt": "2023-10-06T10:02:31+07:00",
                             "offsetDt": "2023-10-06T10:02:31+07:00",
                             "localTime": "10:02:31",
                             "offsetTime": "10:02:31+07:00"
                         }' | jq
{
  "id": "NSK",
  "instantDt": "2023-10-06T03:02:31Z",
  "offsetDt": "2023-10-06T03:02:31Z",
  "localTime": "10:02:31",
  "offsetTime": "10:02:31+07:00"
}
```

## Время в базе данных

Смотрим результат в базе данных:

```text
timecheck=# SELECT * FROM public.times ORDER BY id ASC;
 id  |       instant_dt       |       offset_dt        | local_time | offset_time
-----+------------------------+------------------------+------------+-------------
 MSK | 2023-10-06 10:02:31+03 | 2023-10-06 10:02:31+03 | 10:02:31   | 04:02:31+00
 NSK | 2023-10-06 06:02:31+03 | 2023-10-06 06:02:31+03 | 10:02:31   | 00:02:31+00
(2 rows)
```


## Резюме

Для даты и времени особой разницы между `Instant` и `OffsetDateTime` нет. В конечном итоге оно преобразуется в UTC. 
На фронте используем стандартную функцию `toISOString()`, на бэке - `Instant`.

Для времени есть разница между `LocalTime` и `OffsetTime` при отображении в базе данных. 
Поэтому на фронте переводим в московское время, на бэке используем `LocalTime`.  








