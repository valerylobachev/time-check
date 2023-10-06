alter database timecheck set timezone = 'Europe/Moscow';

CREATE TABLE times
(
    id          varchar(40) PRIMARY KEY,
    instant_dt  timestamptz,
    offset_dt   timestamptz,
    local_time  time,
    offset_time timetz
)
