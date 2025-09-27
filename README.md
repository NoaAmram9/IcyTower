# מבנה פרויקט Icy Tower - Java OOP

## 📁 מבנה תיקיות וקבצים

```
IcyTowerGame/
├── src/
│   └── com/
│       └── icytower/
│           ├── main/
│           │   └── IcyTowerGame.java          # נקודת הכניסה הראשית
│           │
│           ├── core/
│           │   ├── GameManager.java           # Singleton - ניהול מרכזי
│           │   ├── GameEngine.java            # מנוע המשחק הראשי  
│           │   ├── GameObject.java            # מחלקת בסיס לאובייקטים
│           │   └── World.java                 # ניהול עולם המשחק
│           │
│           ├── entities/
│           │   ├── Player.java                # מחלקת השחקן
│           │   ├── Platform.java              # מחלקת הפלטפורמות
│           │   └── Particle.java              # מחלקת חלקיקים
│           │
│           ├── enums/
│           │   ├── PlayerState.java           # מצבי השחקן
│           │   └── PlatformType.java          # סוגי פלטפורמות
│           │
│           ├── interfaces/
│           │   ├── GameObserver.java          # Observer Pattern
│           │   ├── MovementStrategy.java      # Strategy Pattern
│           │   ├── GameObjectFactory.java     # Factory Pattern
│           │   ├── Command.java               # Command Pattern
│           │   ├── GameState.java             # State Pattern
│           │   └── EventListener.java         # Event System
│           │
│           ├── states/
│           │   ├── PlayingState.java          # מצב משחק
│           │   ├── MenuState.java             # מצב תפריט
│           │   ├── GameOverState.java         # מצב סיום
│           │   └── PausedState.java           # מצב השהיה
│           │
│           ├── strategies/
│           │   ├── DefaultMovementStrategy.java    # תנועה רגילה
│           │   └── IceMovementStrategy.java        # תנועה על קרח
│           │
│           ├── factories/
│           │   └── PlatformFactory.java       # מפעל פלטפורמות
│           │
│           ├── commands/
│           │   ├── JumpCommand.java           # פקודת קפיצה
│           │   └── PauseCommand.java          # פקודת השהיה
│           │
│           ├── systems/
│           │   ├── InputManager.java          # Singleton - ניהול קלט
│           │   ├── SoundManager.java          # Singleton - ניהול צלילים
│           │   ├── ScoreManager.java          # Singleton - ניהול ניקוד
│           │   ├── AnimationManager.java      # ניהול אנימציות
│           │   ├── ParticleSystem.java        # מערכת חלקיקים
│           │   └── EventSystem.java           # מערכת אירועים
│           │
│           ├── animations/
│           │   ├── Animation.java             # מחלקת בסיס לאנימציה
│           │   ├── IdleAnimation.java         # אנימציית מנוחה
│           │   ├── RunningAnimation.java      # אנימציית ריצה
│           │   └── JumpingAnimation.java      # אנימציית קפיצה
│           │
│           ├── graphics/
│           │   ├── Renderer.java              # מערכת רינדור
│           │   └── Camera.java                # מצלמה
│           │
│           └── utils/
│               ├── ConfigManager.java         # Singleton - ניהול הגדרות
│               └── PerformanceMonitor.java    # Singleton - מעקב ביצועים
│
├── resources/
│   ├── sounds/
│   │   ├── jump.wav
│   │   ├── land.wav
│   │   └── gameOver.wav
│   │
│   ├── images/
│   │   ├── player/
│   │   │   ├── idle.png
│   │   │   ├── running.png
│   │   │   └── jumping.png
│   │   │
│   │   └── platforms/
│   │       ├── normal.png
│   │       ├── ice.png
│   │       └── breakable.png
│   │
│   └── config/
│       └── game.properties            # קובץ הגדרות
│
├── lib/                               # ספריות חיצוניות (אם נדרשות)
│
├── docs/
│   ├── README.md                      # תיעוד הפרויקט
│   ├── API.md                         # תיעוד API
│   ├── DesignPatterns.md              # תיעוד תבניות העיצוב
│   └── UML/                           # דיאגרמות UML
│       ├── ClassDiagram.png
│       ├── SequenceDiagram.png
│       └── StateDigram.png
│
├── tests/
│   └── com/
│       └── icytower/
│           ├── core/
│           │   └── GameManagerTest.java
│           ├── entities/
│           │   └── PlayerTest.java
│           └── systems/
│               └── ScoreManagerTest.java
│
├── build/                             # תיקיית קומפילציה
│   └── classes/
│
├── dist/                              # תיקיית הפצה
│   └── IcyTowerGame.jar
│
├── .gitignore
├── build.xml                          # Ant build file
├── pom.xml                           # Maven build file (אופציונלי)
└── README.md
```

## 🎯 הסבר מבנה החבילות

### **📦 com.icytower.main**
- נקודת הכניסה הראשית למשחק
- אחראית על יצירת חלון ואתחול המערכת

### **📦 com.icytower.core**
- המחלקות הליבה של המשחק
- ניהול מרכזי ומנוע המשחק

### **📦 com.icytower.entities**
- כל האובייקטים במשחק (שחקן, פלטפורמות, חלקיקים)
- מחלקות עם לוגיקה ספציפית לישויות

### **📦 com.icytower.interfaces**
- כל הממשקים לתבניות העיצוב
- הגדרת חוזים בין מחלקות

### **📦 com.icytower.states**
- מימוש State Pattern
- כל מצב משחק במחלקה נפרדת

### **📦 com.icytower.strategies**
- מימוש Strategy Pattern
- אסטרטגיות תנועה שונות

### **📦 com.icytower.systems**
- מערכות משחק מרכזיות (Singletons)
- ניהול קלט, צליל, ניקוד

### **📦 com.icytower.graphics**
- מערכת רינדור ומצלמה
- כל הקשור לציור והצגה

### **📦 com.icytower.utils**
- כלים עזר וניהול הגדרות
- מחלקות תמיכה כלליות

## 🔧 כיצד לבנות הפרויקט

### **באמצעות Command Line:**
```bash
# קומפילציה
javac -d build/classes -sourcepath src src/com/icytower/main/IcyTowerGame.java

# הרצה
java -cp build/classes com.icytower.main.IcyTowerGame
```

### **באמצעות IDE (IntelliJ/Eclipse):**
1. צור פרויקט Java חדש
2. הגדר את `src` כ-Source Folder
3. הגדר את `resources` כ-Resources Folder
4. הגדר את החבילה הראשית: `com.icytower`
5. הוסף את הקבצים לחבילות המתאימות

### **באמצעות Maven (pom.xml):**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.icytower</groupId>
    <artifactId>icy-tower-game</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
            </plugin>
        </plugins>
    </build>
</project>
```

## 🎨 עקרונות עיצוב שמומשו

### **1. Single Responsibility Principle**
כל מחלקה אחראית על תחום אחד בלבד

### **2. Open/Closed Principle** 
ניתן להרחבה ללא שינוי קוד קיים (Strategy, State)

### **3. Dependency Inversion**
תלות בממשקים ולא במימושים קונקרטיים

### **4. Separation of Concerns**
הפרדה ברורה בין UI, לוגיקה, ונתונים

### **5. Modularity**
כל תכונה במודול נפרד וניתן להחלפה

## 📋 יתרונות המבנה

- **קל לתחזוקה**: כל תכונה במקום מוגדר
- **ניתן להרחבה**: הוספת תכונות ללא שבירת קוד
- **בדיקות**: כל מחלקה ניתנת לבדיקה בנפרד  
- **עבודת צוות**: מפתחים יכולים לעבוד על חלקים שונים
- **קריאות**: מבנה ברור והיגיון ארגון

המבנה הזה מאפשר פיתוח מקצועי, תחזוקה קלה והרחבה עתידית!
