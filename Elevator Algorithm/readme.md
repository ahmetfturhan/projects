This is an elevator algorithm that finds the shortest path through a queue.
Some different usecases:
 If there are two different closest floor with the same distance.
            For example: current floor is 3 and there is 2 and 4 in the queue, left is the value 2, right is the value 4.
            We find these values by adding and subtracting the lowestDistance that we found earlier to the currentfloor value.
            In this case: Our lowest distance is 1. If we subtract 1 from the current floor: 3-1 = 2. 2 is the leftmost value.
            If we add 1 to the current floor value: 3+1 = 4. 4 is the rightmost value.
            
            We need to find these left and right values to detect "If there are two different closest floor with the same distance.", in this case
            we need to find the shortest path.
            For example: The current floor is 3 and there is [0, 1, 2, 4, 8] in the queue, if the elevator moves to the up, it needs to travel through 13 floors to complete the queue.
            But if it moves to the down, it needs to travel 11 floors to complete the queue, which is a shorter path. So we are finding the shortest path, if there are two different closest floor with the same distance.
