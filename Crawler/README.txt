%%1.SRC file are included in assignmenttwo folder, this file contains all source code and lib for running the code.

%%2. We have finished all questions including bonus, please refer to another answers.pdf file in same folder for detailed description, including our brief analysis, assumptions corresponding to specific question and more clear formatted answers (NOTE: our direct generated .txt may look wierd in Windows system, please put them in eclipse or refer to our answer.pdf or paste them into word for better view.)

%%3. In order to save memory to avoid crash, we use separate class to do separate question. Please refer to these .java file in src file according to %%7. 

First, we use crawler to get raw page text containing all text information of pages and log file inclusing basic inforation about URL.

Then, we use raw pagetext file to generate TokenFile only containing tokens.

Finally, just process these tokens to answer according questions, efficent and clear. For each question, you can generate .txt file containing answers.

%%4. Please ensure you have JDK 7.0 for testing. 

MOST IMPORTANT!!! Allocate heap memory to 2.5GB for running to avoid OutOfMemory error.

%%5. We have found one bug and put it into Bug.pdf

%%6. other details could be discussed in face to face meeting.

%%7. Java file Description:
Crawler.java used for starting cralwer/*After running this program, you can get txt files for further processing*/

CrawlerControl.java used for controlling cralwer

GenerateTokenFile.java produce tokenized txt file

Utilities.java provide tokenizing processing support method

compareByFrequency.java provides result ranking method

The .java files with "Answer" name answer the assignment questions and generate corresponing file indicating answers.

The other .java files with "Cluster" name answer bonus question.
