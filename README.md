# CanvasSubmissionExtractor

# What is it?

Canvas is an online learning portal. Unfortunately, it doesn't have functionality to
download submission for each section. In a large number of class, there are multiple section
and it become cumbersome to donloawd individual files. However, canvas has functionality to 
download submission for the whole class. So, this executable uses the student-section map from
an excel file to retrieve submission file of students for a given section from large submission file.

# How to use it?

# 1.
Download the jar file.
# 2.
Place extracted submissions file and excel file in a folder where the jar file is located.

# Excel file Note
The excel file must have two columns only. 
First column must be student names and second column must be the section name for that student.

# 3.

# For single section: 
Enter section name and hit extract. Example: 1123
This will create a folder with section name and sub folders for each student with their submission file.

# For multiple sections:
Enter each section followed by a comma and hit extract. Example: 11H5,1140,1123.
This will create individual folders for each sections and subfolders for each students with their submission file.
