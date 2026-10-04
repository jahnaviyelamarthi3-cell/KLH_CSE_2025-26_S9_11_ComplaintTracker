Complaint tracker
A text-based system designed to efficiently manage and search large collections of customer complaints using string-matching algorithms.

Project Information

Course: Data Structures and Algorithms - 3 (25CS2103E)

Academic Year: 2026–2027

Team Number: 11

Section: 9

Guide: Dr. S. Vinay Kumar, Associate Professor, Department of Computer Science and Engineering

Team Members

Name

Roll Number

Y. Jahnavi

2520030567

P. Devisree

2520030594

K. Shresta Priya

2520030127

Abstract

The Complaint Tracker is a text-based system designed to efficiently manage and search large collections of customer complaints. The system organizes complaints using unique complaint IDs, categories, descriptions, statuses, priorities, and resolutions.

A structured corpus containing multiple complaint records is used to demonstrate efficient pattern searching. The project implements string-matching algorithms such as Knuth-Morris-Pratt (KMP), Z-Algorithm, and Rabin-Karp with rolling hash to locate specific complaint patterns within the corpus. These algorithms reduce unnecessary comparisons and improve searching efficiency compared with simple brute-force searching, especially when processing large text datasets.

Each complaint is maintained as an individual file, and all records are combined into a single corpus for large-scale testing. Search results identify matching complaint records and their IDs.

Objectives

Organize complaint records with unique IDs and relevant details.

Maintain complaint records as individual text files and use them as a searchable corpus.

Search for patterns or keywords in complaint descriptions.

Demonstrate efficient string-matching techniques on complaint data.

Identify matching complaint records and their IDs.

Algorithms

The project focuses on these string-matching algorithms:

Knuth-Morris-Pratt (KMP): Uses an LPS (Longest Prefix Suffix) array to avoid repeated comparisons while searching.

Z-Algorithm: Uses a Z-array to determine prefix matches and support pattern searching.

Rabin-Karp: Uses hashing, including a rolling hash, to find pattern matches in text.

The abstract describes these algorithms as ways to reduce unnecessary comparisons and improve search efficiency over brute-force searching.

Corpus

The corpus contains complaint records stored as text documents. Each complaint is maintained as an individual file, and the files are used together for testing searches across a larger collection.

Example structure:

ComplaintTracker/
├── corpus/
│   ├── complaint1.txt
│   ├── complaint2.txt
│   ├── complaint3.txt
│   └── ...
├── ComplaintTrackerKMP.java
└── README.md

The filenames above are examples. Adjust the structure to match the files and algorithms currently present in your repository.

Current Implementation

The current demonstration uses KMP pattern searching on the complaint text files in the corpus folder.

Run the KMP demonstration (Java)

Install a Java Development Kit (JDK).

Clone or download this repository.

Open a terminal in the project folder.

Ensure the complaint .txt files are inside a folder named corpus.

Compile and run:

javac --release 8 ComplaintTrackerKMP.java
java ComplaintTrackerKMP

Enter a keyword or pattern when prompted. The program reports the files that contain a match.

Note: The commands above apply to the current KMP Java demonstration. Add the run instructions for the Z-Algorithm and Rabin-Karp implementations when those programs are added.

Expected Outcome

Efficient retrieval of complaint records that match a search pattern.

Search results that identify matching complaint records and their IDs.

A practical demonstration of string-matching algorithms on a complaint corpus.

A comparison of efficient pattern-search techniques with simple brute-force searching.

Technologies

Programming language: Java (for the current KMP demonstration)

Data: Complaint text files organized as a corpus

Core topic: String matching and pattern searching
