#Bubble Sort
#Author: Ahmet Faruk Turhan
#Date: 17.11.2021
#For the sake of practice

a = [8, 5, 6, 2, 0, 9, 3, 1, 7, 4]
print("Before Bubble Sort:", a)

for i in range(0, len(a)):
    for j in range(len(a)-1, i, -1):
        if a[j] < a[j - 1]:
            a[j], a[j - 1] = a[j - 1], a[j]
        j -= 1

print("After Bubble Sort:", a) #print the array