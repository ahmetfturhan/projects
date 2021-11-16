#Selection Sort
#Author: Ahmet Faruk Turhan
#Date: 17.11.2021
#For the sake of practice

a = [8, 5, 6, 2, 0, 9, 3, 1, 7, 4]
print("Before Selection Sort:", a)

for i in range(len(a)):
    smallestValueIndex = i
    for j in range(i, len(a)):
        if a[j] < a[smallestValueIndex]:
            smallestValueIndex = j
    a[i], a[smallestValueIndex] = a[smallestValueIndex], a[i]

print("After Selection Sort:", a) #print the array