# Student Grades Processor

A Java console application for processing student grade data and generating frequency and statistics files.

## Overview

This application reads student scores from a CSV file, performs calculations, and generates two text files:

- `frequency_data.txt`: Contains the frequency of each score.
- `statistics_data.txt`: Contains mean, median, and mode of all scores.

## Prerequisites

- Java (JDK) installed on your machine.

## Getting Started

1. Clone the repository:

    ```bash
    git clone <repository_url>
    ```

2. Navigate to the project directory:

    ```bash
    cd student-grades-processor/src/main/java
    ```

3. Compile and run the Java program:

    ```bash
    javac Main.java
    java Main
    ```

## Usage

1. When the program starts, you will see a menu with options:

    - 1: Generate file .txt Pengelompokan frekuensi data berdasarkan jumlah nilai
    - 2: Generate file .txt yang menyajikan mean, median, modus pada data_sekolah.csv
    - 3: Generate kedua file pada nomor 1 dan 2
    - 0: Exit

2. Choose an option by entering the corresponding number and press Enter.

3. Follow the instructions to complete the selected operation.

## Sample CSV Data

The program comes with sample CSV data in-memory for testing purposes. You can customize the data by modifying the `loadStudentScores` method in the code.

## File Output

Generated files (`frequency_data.txt` and `statistics_data.txt`) will be stored in the `src/main/java/output` directory.

## Author

Muhammad Alfian Nurul Yaqien
