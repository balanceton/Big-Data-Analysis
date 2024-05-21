## Project Overview

In the age of big data, data analysis is an important requirement with speed and efficiency. This project aims to perform various descriptive statistical operations on large data sets using the Hadoop platform. Our goal is to provide users with a tool to analyze large data sets and demonstrate the power and flexibility of Hadoop in the process.

## 1. Implementation Environment

- **Platform**: Hadoop 3.4.0
- **Programming Language**: Java 8
- **Development Tools**: Linux Terminal, Eclipse IDE, Hadoop HDFS, MapReduce
- **Libraries and APIs**: Hadoop Common, Hadoop MapReduce, Java AWT/Swing (for GUI)

## 2. DataSet

The data set used includes information about job postings published on LinkedIn. The dataset is located in the `jobs.csv` file and contains the following columns:

- **job_link (str)**: URL link to the job posting on LinkedIn.
- **last_processed_time (datetime)**: The time when the job posting was last processed.
- **got_summary (bool)**: Whether the job summary was extracted successfully.
- **got_ner (bool)**: Whether Named Entity Recognition (NER) operation was performed.
- **is_being_worked (bool)**: Whether the job posting is currently being processed.
- **job_title (str)**: Title of the job posting.
- **company (str)**: Name of the company offering the job position.
- **job_location (str)**: Location of the job position.
- **first_seen (datetime)**: The time the job posting was first seen.
- **search_city (str)**: The city where job postings are collected.

The dataset can be found at the following link: [1.3M LinkedIn Jobs and Skills 2024](https://www.kaggle.com/datasets/asaniczka/1-3m-linkedin-jobs-and-skills-2024?resource=download&select=linkedin_job_postings.csv).

## 3. Descriptive Statistics Functions

1. **Job Posting Hours**: This function calculates the time at which jobs were posted and identifies that most job advertisements were posted at 09:00.

2. **The City with the Most Job Postings**: This function identifies the city with the highest number of job postings using MapReduce.

3. **Percentage of Jobs**: This function calculates the ratio of whether the jobs advertised are currently being worked on or not.

4. **The City with the Least Job Postings**: This function identifies the city with the fewest job postings using MapReduce.

5. **Standard Deviation**: This function calculates the standard deviation of a specific column (timestamp) in the dataset using Hadoop MapReduce. The Mapper class reads the data and emits each timestamp value, while the Reducer class calculates the mean, variance, and standard deviation of these values.

## 4. Implementation

### Setting Up the Environment

1. **Install Hadoop 3.4.0**: Follow the official Hadoop installation guide to set up Hadoop on your Linux machine.
2. **Java 8**: Ensure that Java 8 is installed on your system.
3. **Development Tools**: Use the Linux terminal for command-line operations and Eclipse IDE for Java development.
4. **HDFS**: Set up Hadoop Distributed File System (HDFS) for storing the dataset.
5. **MapReduce**: Implement MapReduce jobs for performing the descriptive statistical operations.

### Running the Project

1. **Load Dataset**: Upload the `jobs.csv` file to HDFS.
2. **Compile Java Code**: Use Eclipse IDE to compile the Java programs.
3. **Run MapReduce Jobs**: Execute the MapReduce jobs to perform the various descriptive statistical operations.

### GUI (Optional)

For a graphical user interface, use Java AWT/Swing libraries to create a simple GUI application that can interact with the Hadoop backend and display the results of the statistical operations.

## 5. Conclusion

This project demonstrates the capabilities of the Hadoop platform in processing and analyzing large datasets. By implementing various descriptive statistical operations, users can gain insights into job postings on LinkedIn, showcasing the power and flexibility of Hadoop in handling big data.

---

For any questions or further assistance, please refer to the project documentation or contact the project maintainers.
