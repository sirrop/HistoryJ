#HistoryJ: History Management API for Java

## What is it?

HistoryJ is a history management api for Java.

## How to use

1. Create a instance.
   ```java
    private History<URL> history = new ArrayHistory(10);
   ```
2. If you want to use a synchronized instance, wrap it with Histories#synchronizedHistory(History).
   ```java
    try {
        history = Histories.synchronizedHistory(history);
    } catch (IOException e) {
        e.printStackTrace();
    }
   ```
3. Start history management
    ```java
     history.add(new URL("https://github.com/sirrop/HistoryJ.git"));
    ```
   ```java
    URL before = hisory.undo();
   ```