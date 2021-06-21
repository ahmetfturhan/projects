from termcolor import colored
cls = lambda: print('\n'*100)

def getPoints(letterGrade):
    letterGrade = letterGrade.upper()
    value = 9999
    if letterGrade == "A":
        value = 4.00
    elif letterGrade == "A-":
        value = 3.70
    elif letterGrade == "B+":
        value = 3.30
    elif letterGrade == "B":
        value = 3.00
    elif letterGrade == "B-":
        value = 2.70
    elif letterGrade == "C+":
        value = 2.30
    elif letterGrade == "C":
        value = 2.00
    elif letterGrade == "C-":
        value = 1.70
    elif letterGrade == "D+":
        value = 1.30
    elif letterGrade == "D":
        value = 1.00
    elif letterGrade == "F":
        value = 0.00
    elif letterGrade == "S":
        value = 0.00
    else:
        value = None
    return value

num_of_lessons = int(input("Enter the number of lessons: "))
letterGradeList = []
for i in range(0, num_of_lessons):
    print(colored("-----------------------------------------------------------------------", "red", "on_magenta"))
    print(colored(f"{i}: Enter the Letter grade and the ECTS of the course with a space:", "green"))
    print(colored("Example:", "blue"))
    print(colored("B+ 6", "red"))
    userInput = input()
    splitInput = userInput.split(" ")
    letterGradeList.append(splitInput)
totalECTS = 0
letterECTSValue = 0
skipped = 0
for i in range(0, len(letterGradeList)):
    if letterGradeList[i][0] == "S" or letterGradeList[i][0] == "I":
        skipped += 1
        continue
    else:
        totalECTS += float(letterGradeList[i][1])
        gradeValue = float(getPoints(letterGradeList[i][0]))
        letterECTSValue += float(letterGradeList[i][1]) * gradeValue

GPA = letterECTSValue / totalECTS
GPA = str(round(GPA, 2))
cls()
print(colored(f"Your GPA is {GPA}", "red", "on_green", attrs=['bold']))
print(colored(f"Total ECTS used in calculation: {totalECTS}", "blue", "on_grey", attrs=['blink']))
print(colored(f"Skipped Courses: {skipped}", "red", "on_cyan", attrs=['dark']))