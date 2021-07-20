#author: Ahmet Faruk Turhan
#date: 20.07.2021
#This is an elevator algorithm that finds the shortest path through a queue.
#Elevator specs: Can move upwards and downwards, user can press a floor button at every floor, still finds the shortest path.
from termcolor import colored
import copy

print(colored("Enter the floors that you want to go to:", "blue", "on_red", attrs=['bold']))
print(colored("Available Floors: 0-10", "blue", "on_red", attrs=['bold']))
isEnd = False
floorQueue = []
currentFloor = 0
pathOfElevator = []


# To allow people to press floor buttons
def addFloors():
    while True:
        print()
        print(colored("CURRENT FLOOR " + str(currentFloor), "red", "on_white", attrs=['bold']))
        print(colored("--------------------------------------------------------------------", "red", "on_magenta"))
        print(colored("Enter a floor: (type 99 to continue)", "blue", attrs=['bold']))
        print(colored("Current floor queue: " + str(floorQueue), "white", attrs=['reverse']))

        try:
            floor = int(input())  # get the input as an integer
            if floor == 99:  # continue
                break
            if not 0 <= floor <= 10:  # if the input doesn't inside the floor range
                raise Exception("Enter an integer [0-10]")
            if floor in floorQueue:  # if the pressed floor button is already on the queue,
                continue
            else:
                floorQueue.append(floor)  # add the floor to the queue
                floorQueue.sort()  # sort the list
        except:
            print("Misinput:\nEnter an integer [0-10]")


def findShortestPath():
    rightQ = copy.deepcopy(floorQueue)
    # print("CURRENT FLOOR", currentFloor)
    # go right
    for k in floorQueue:
        if k < currentFloor:
            rightQ.remove(k)
    rightResult = abs(rightQ[len(rightQ) - 1] - currentFloor)
    # print("Right Result", rightResult)
    # print("RightQ", rightQ)

    leftQ = copy.deepcopy(floorQueue)
    # go left
    for j in floorQueue:
        if j > currentFloor:
            leftQ.remove(j)
    leftResult = abs(leftQ[0] - currentFloor)
    # print("LeftQ", leftQ)
    # print("Left Result:", leftResult)

    if leftResult < rightResult:
        return "Left"
    else:
        return "Right"


while True:
    addFloors()  # People can press the floor buttons at every travelled floor.
    floorQueue.sort()
    lowest = 999
    closestFloor = 931
    onlyOne = False
    if len(floorQueue) == 0:  # if there is no floors in the queue, break the elevator loop
        break
    if len(floorQueue) == 1:  # if there is only one floor in the queue, directly go into that floor.
        closestFloor = floorQueue[0]
        onlyOne = True

    if not onlyOne:  # if there is more than one floor,

        # to find the lowest distance between the current floor and the closest floor in floor queue
        lowestDistance = 84953
        for r in floorQueue:
            currentDistance = abs(r - currentFloor)
            if currentDistance < lowestDistance:
                lowestDistance = currentDistance

        # to find the closest floor to the current floor
        for i in floorQueue:
            operation = abs(i - currentFloor)
            if operation < lowest:
                lowest = operation
                closestFloor = i  # lowest floor value

            """
            If there are two different closest floor with the same distance.
            For example: current floor is 3 and there is 2 and 4 in the queue, left is the value 2, right is the value 4.
            We find these values by adding and subtracting the lowestDistance that we found earlier to the currentfloor value.
            In this case: Our lowest distance is 1. If we subtract 1 from the current floor: 3-1 = 2. 2 is the leftmost value.
            If we add 1 to the current floor value: 3+1 = 4. 4 is the rightmost value.
            
            We need to find these left and right values to detect "If there are two different closest floor with the same distance.", in this case
            we need to find the shortest path.
            For example: The current floor is 3 and there is [0, 1, 2, 4, 8] in the queue, if the elevator moves to the up, it needs to travel through 13 floors to complete the queue.
            But if it moves to the down, it needs to travel 11 floors to complete the queue, which is a shorter path. So we are finding the shortest path, if there are two different closest floor with the same distance.
            """

            down = currentFloor - lowestDistance
            up = currentFloor + lowestDistance

            if down in floorQueue and up in floorQueue:
                # print("Two same distance, looking for upper lower.")
                condition = findShortestPath()
                if condition == "Left":
                    closestFloor = down  # go down
                    # print("Left", lowestI)
                if condition == "Right":
                    closestFloor = up  # go up
                    # print("Right", lowestI)
                break

    floorQueue.remove(closestFloor)  # remove the closest floor because closest floor is now the current floor.
    floorQueue.sort()  # sort the queue
    thisString = "Going to " + str(closestFloor) + " th floor."
    print(colored(thisString, "blue"))
    pathOfElevator.append(closestFloor)  # to see the path of the elevator
    currentFloor = closestFloor

print("This is the path of the elevator:", pathOfElevator)
