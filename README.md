# JustMC JVM
JustMC JVM (*далее - JJVM*) - Транслятор байт-кода JVM в блочный код [JustMC](https://justwiki.gitbook.io/wiki/creative/about).

### Разработка
Проект находится в разработке. Могут быть баги, сильные баги, ужасные баги и т.п. На данный момент есть работа с примитивами джавы, вечный цикл, условия (даже с иначе), вывод сообщения игроку, некоторые события. Примеры кода можно найти в модуле [justmc-jvm-test](justmc-jvm-test/src/main/java/Main.java).

### Как это работает
Вы пишете код на Java/Kotlin, компилируете в jar-файл, прогоняете этот jar-файл через данный транслятор; и он вам выдаёт json-файл с кодом JustMC, который можно загрузить на сервер командой `/module loadUrl`.

### Установка
1. Создаём проект на Java или Kotlin, выбрав Gradle и Kotlin DSL.
2. В файл `build.gradle.kts` добавляем следующее:
```kts
apply("https://raw.githubusercontent.com/unidok/justmc-jvm/refs/heads/master/jjvm.gradle.kts")
```
3. В файл `gradle.properties` добавляем значения:
```properties
justmc.jvm.version=1.0
justmc.jdk.version=1.0
justmc.dir.out=out
```
4. Перезагружаем Gradle (должна появиться кнопочка справа сверху)
5. Можно начинать работу
### Настройка
После установки у вас должно появиться 2 новых таска gradle в группе `justmc`: 
- `buildModule` - сборка модуля в json-файл 
- `uploadModule` - сборка модуля в json-файл + загрузка на сервер (выдаст ссылку)

После запуска транслятора у вас создастся файл конфигурации `jjvm-config.json`, который выглядит так:
```json5
{
    "isModule": false, // Является ли код независимым (если false, то будет применено больше оптимизаций (В РАЗРАБОТКЕ)) , а так же команда для upload будет содержать force
    "sourceLineNumbers": false, // Сохранение в коде номеров строк
    "exceptionStackTrace": false, // Показ стек трейса при ошибке (В РАЗРАБОТКЕ)
    "debug": false, // Выводить ли в терминал отладочную информацию: классы, методы, байткод, промежуточное представление (полезно)
    "prettyOutput": false // Красивый вывод json в файл
}
```


---
### Java
Идея JJVM - сделать возможным писать привычный код на Java и запускать его на JustMC.
Поэтому транслированный код будет работать в соответствии со спецификацией JVM (по возможности).
<details>
<summary><b><i>native-методы</i></b></summary>
    
Большинство native-методов из Java не будут реализованы по причине того, что такой возможности нет на JustMC.
Например, методы из `sun.misc.Unsafe`.
</details>


---
### Примитивы
Все базовые примитивы (`boolean`, `byte`, `short`, `char`, `int`, `long`, `float`, `double`) будут восприняты как примитив числа JustMC.
<details>
<summary><b><i>Числа с плавающей точкой</i></b></summary>
    
Работа чисел с плавающей точкой может отличаться от стандарта IEEE 754, т.к. на JustMC для простоты используется округление до 10 знаков,
чтобы 0.1 + 0.2 было равно 0.3, а не 0.30000000000000004 - этот "остаток" отбрасывается при округлении.
Но это касается только сложения, вычитания, сравнения и т.д. В точных действиях по типу корней, логарифмов и т.п. числа работают как надо.
Ещё важно понимать, что на JustMC не существует float: он будет работать точно так же, как и double.
</details>
Помимо базовых примитивов, примитивами считаются значения с JustMC: Текст, Вектор, Список и др.
Хоть они и описаны в [JJDK](https://github.com/unidok/justmc-jvm/blob/master/justmc-jdk/src/main/java/justmc) как классы, но при их создании не выделяется память в куче, в отличие от настоящих [объектов](#объекты).


---
### Структуры
В JJVM добавлены структуры. Структура строится на основе примитивов с JustMC.\
Структуры, как и примитивы:
- Неизменяемые
- Копируются по значению
- Не хранят данные в куче (помещаются целиком на стек)
- Не являются объектами
- Не реализуют никаких интерфейсов

<details>
<summary><b><i>Объявление структуры</i></b></summary>

```java
import justmc.*;

public final class AStruct extends Primitive {
    // ^^^^^               ^^^^^^^^^^^^^^^^^
    // Обязательно final.  Чтобы JJVM понимал, что перед нами структура, а не класс, наследуем justmc.Primitive
    
    public final int         intField;
    public final Location    primitiveField;
    //     ^^^^^ ^^^^^^^^
    // Все поля обязательно final и хранят только примитивы.
    // Структуры не могут хранить объекты, потому что не обрабатываются GC.
    
    public AStruct(int intField, Location primitiveField) {
        this.intField = intField;
        this.primitiveField = primitiveField;
    }
}
```
</details>

<details>
<summary><b><i>Возврат структуры из метода</i></b></summary>

Как следует из свойств структуры, она полностью помещается на стек. 
Это означает, что при возврате структуры из метода, создастся столько переменных на возврат, сколько полей имеет структура.
```java
// Какой-то метод, который возвращает структуру
AStruct method();

void main() {
    AStruct aStruct = method(); // Элементы структуры хранятся на стеке (каждый элемент в своей строчной переменной)
    sendMessage(aStruct.intField);
}

// Как это выглядит под капотом (псевдокод):

// Метод возвращает значения в две переменные
method(Variable intField, Variable primitiveField);

main() {
    int intField;
    Location primitiveField;
    intField, primitiveField = method(); // Присвоение нескольких переменных
    sendMessage(intField);
}
```
</details>

<details>
<summary><b><i>Передача структуры как аргумент</i></b></summary>
    
При передаче структуры в метод, под каждый элемент структуры будет создан свой параметр.
```java
// Какой-то метод, принимающий структуру:
void method(AStruct aStruct);

void main() {
    AStruct aStruct = new AStruct(1, Location.of(0, 0, 0)); // По-прежнему 2 переменные
    method(aStruct); // Передаём структуру
}

// Как это выглядит под капотом (псевдокод):

// Метод принимает 2 значения
method(int intField, Location primitiveField);

main() {
    int intField = 1;
    Location primitiveField = Location.of(0, 0, 0);
    method(intField, primitiveField); // Передача двух аргументов
}
```
</details>

<details>
<summary><b><i>Хранение структур в единичном контейнере</i></b></summary>
    
При записи структуры в единичный контейнер (значение списка, ключ или значение словаря, делегированная переменная и т.д.) все элементы структуры упакуются в новый список, который и будет инстансом структуры.
```java
void main() {
    AStruct aStruct = new AStruct(1, Location.of(0, 0, 0));
    ListPrimitive<AStruct> list = ListPrimitive.of(aStruct); // Создаём список из структур
}

// Как это выглядит под капотом (псевдокод):
main() {
    int intField = 1;
    Location primitiveField = Location.of(0, 0, 0);
    ListPrimitive aStruct = ListPrimitive.of(intField, primitiveField); // упаковка структуры
    ListPrimitive list = ListPrimitive.of(aStruct); // Список из списков (структур)
}
```
</details>


---
### Объекты
В JVM доступ к объекту происходит по ссылке. Ссылка, как и другие примитивы, будет воспринята как число.\
Специальная константа `null` эквивалентна числу 0, когда речь идёт об объектах, а когда речь о примитивах, то null означает пустое значение: `{}`.
#### Создание объекта
При создании объекта выделяется память в куче для него, а затем возвращается ссылка на этот объект.\
Посмотреть, как работает куча, можно в [justmc-jdk/src/main/java/justmc/Heap.java](https://github.com/unidok/justmc-jvm/blob/master/justmc-jdk/src/main/java/justmc/Heap.java)


