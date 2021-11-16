#Insertion Sort
#Author: Ahmet Faruk Turhan
#Date: 17.11.2021
#For the sake of practice
a = [8, 5, 6, 2, 0, 9, 3, 1, 7, 4]
print("Before Insertion Sort:", a)
j = 1  # sorted array's last index + 1
for i in range(1, len(a)):
    insertionIndex = i
    # find the insertion index
    for k in range(j - 1, -1, -1): #from sortedArray's last index to 0
        if a[i] < a[k]:
            insertionIndex = k
    currentItem = a[i]
    # Shift items to right
    for z in range(i - 1, insertionIndex - 1, -1): #from currentItem's index-1 to insertionIndex
        a[z + 1] = a[z] #shift to right by one
    a[insertionIndex] = currentItem #insert the item
    j += 1 #increment the sorted array index
print("After Insertion Sort:", a) #print the array
