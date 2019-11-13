## Тестовый сервис получения данных о погоде.

Сервис позволяет простым rest запросом получить список поддерживаемых сервисов (ссылка при локальном запуске)    

`http://localhost:8080/weather/providers`

И погоду в выбранном сервисе по интересующему городу (ссылка при локальном запуске)  

`http://localhost:8080/weather/get?city=naryan-mar&provider=accu`
    
Настройки погодных сервисов лежат в 
`/src/main/resources/providers.properties`

Ключи доступа к API погодных сервисов в  
`/src/main/resources/keys.properties` 
в виде  
`имя_сервиса.key=апи_ключ_сервиса`

Пример:  
`## OpenWeatherMap API key`
`openweather.key=413a634443158c6b34287a0d3eb3469c175ee9`

Имя сервиса должно быть одинковым во всем файле настроек.  
Пример используемых параметров погодного сервиса: 
``` 
## Строка запроса данных о погоде
## {0} - место подстановки города
## {1} - место подстановки API ключа
имя_сервиса.request.weather.url=http://dataservice.accuweather.com/currentconditions/v1/{0}?apikey={1}&language=ru&details=true

## Строка запроса кода города по имени города(для некоторых сервисов)
имя_сервиса.request.city.url=http://dataservice.accuweather.com/locations/v1/search?q={0}&apikey={1}

## Строка поиска значения по ключу в Json объекте при помощи Json-Path
имя_сервиса.city=$.[0].Key
имя_сервиса.temp=$.[0].Temperature.Metric.Value
имя_сервиса.hum=$.[0].RelativeHumidity
имя_сервиса.press=$.[0].Pressure.Metric.Value

##  Признак двухшагового запроса данных о погоде
имя_сервиса.two.step.request=true
```