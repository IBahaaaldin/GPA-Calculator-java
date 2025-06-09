# 🎓 Ultimate GPA Calculator (Java Version)

![Java GPA Calculator Banner](assets/java_project_banner.png)

![Java](https://img.shields.io/badge/Language-Java-orange)
![License](https://img.shields.io/badge/License-MIT-blue)
![Status](https://img.shields.io/badge/Version-1.0-brightgreen)

A full-featured Java-based GPA management program for students. Run in the command line or GUI to track and manage their courses and GPA effectively.

---

## 👤 About the Developer

This project was created by **IBahaaaldin**, a second-year Computer Science student with a focus on Artificial Intelligence. Passionate about building practical tools that solve real student problems using clean code, useful interfaces, and continuous self-improvement through hands-on learning.

---

## 🧠 Project Overview

The **Ultimate GPA Calculator** was designed for university students who want to:

- Track their GPA with ease
- Save and load their academic data
- Use either CLI or GUI to manage courses
- Export clean reports with GPA and course lists

---

## ✨ Features

### ✅ CLI Version (`GPACalculator.java`)
- Add, edit, and remove courses
- Full grade scale (A to F) support
- GPA calculation with `.txt` report export
- Save/load data from `courses.csv`

### ✅ GUI Version (`GPACalculatorGUI.java`)
- Built with **Swing**
- Add courses with a form
- View and manage course list in a table
- Calculate GPA and export report

---

## 📷 Screenshots

### CLI Mode
```
Welcome to GPA Calculator
1. Add course
2. Edit course
3. Remove course
4. Display courses
5. Calculate GPA
6. Save and Exit
```

### GUI Mode

![GUI Preview](assets/gui_preview.png)

---

## 📂 File Structure

```
gpa-calculator-java/
├── src/                                # Java source files (CLI + GUI)
│   ├── GPACalculator.java              # Command Line GPA Calculator
│   └── GPACalculatorGUI.java           # Swing-based GUI Calculator
│
├── data/                               # Input files for GPA data
│   └── courses.csv                     # Sample course records
│
├── assets/                             # Images used in README.md
│   ├── java_project_banner.png         # Project header image
│   └── gui_preview.png                 # Screenshot of the GUI interface
│
├── LICENSE                             # MIT license for open source use
└── README.md                           # Full documentation and usage guide
```

---

## ▶️ How to Run

### Compile & Run CLI:
```bash
javac GPACalculator.java
java GPACalculator
```

### Compile & Run GUI:
```bash
javac GPACalculatorGUI.java
java GPACalculatorGUI
```

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 💡 Future Ideas

- Add semester tagging and filtering
- GPA graph visualization (line/bar charts)
- Export to PDF or JSON
- Convert to mobile (Android or Swift)

---

## 🔁 Related Projects

**Check out the Python version of this project:**  
👉 [GPA Calculator (Python)](https://github.com/IBahaaaldin/gpa-calculator-python)

---

_This project reflects the mindset of a student solving real academic challenges with professional-grade code and design._
