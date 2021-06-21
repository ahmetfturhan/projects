# Name: Ahmet Faruk Turhan
# Student ID: 041901038
# Date: 22.03.2021

# IMPORTED MODULES
#-----------------------------------------------------------------------------------
from tkinter import *
from tkinter import filedialog
from PIL import *
from PIL import Image, ImageDraw, ImageOps, ImageFont, ImageFile # Python Imaging Library (PIL) modules
import numpy as np # fundamental Python module for scientific computing
import os
import pyautogui as pya

# MAIN FUNCTION OF THE PROGRAM
#-----------------------------------------------------------------------------------
# Main function where this python script starts execution
def main():
   programTitle = "Character Recognition - Ahmet Faruk Turhan"
   while True:

      # --------------------------------------------------------------------------------
      # path of the current directory where this program file is placed
      curr_dir = os.path.dirname(os.path.realpath(__file__))
      path, threshold_value = GUI(programTitle, curr_dir)
      img_file = path
      img_color = Image.open(img_file)
      img_color.show()  # display the color image
      # convert the color image to a grayscale image
      # --------------------------------------------------------------------------------
      img_gray = img_color.convert('L')
      #img_gray.show()  # display the grayscale image
      # create a binary image by thresholding the grayscale image
      # --------------------------------------------------------------------------------
      # convert the grayscale PIL Image to a numpy array
      arr_gray = np.asarray(img_gray)
      # values below the threshold are considered as ONE and values above the threshold
      # are considered as ZERO (assuming that the image has dark objects (e.g., letters
      # ABC or digits 123) on a light background)
      THRESH = threshold_value
      print("Threshold = "+ str(threshold_value))

      ZERO, ONE = 0, 255
      # the threshold function defined below returns the binary image as a numpy array
      arr_bin = threshold(arr_gray, THRESH, ONE, ZERO)

      # convert the numpy array of the binary image to a PIL Image
      img_bin = Image.fromarray(arr_bin)
      img_bin.show()  # display the binary image
      # component (object) labeling based on 4-connected components
      # --------------------------------------------------------------------------------
      # blob_coloring_8_connected function returns a numpy array that contains labels
      # for the pixels in the input image, the number of different labels and the numpy
      # array of the image with colored blobs
      arr_labeled_img, num_labels, arr_blobs = blob_coloring_8_connected(arr_bin, ONE)
      # print the number of objects as the number of different labels
      print("There are " + str(num_labels) + " objects in the input image.")
      # write the values in the labeled image to a file

      # convert the numpy array of the colored components (blobs) to a PIL Image
      img_blobs = Image.fromarray(arr_blobs)
      #img_blobs.show()  # display the colored components (blobs)

      img_arr = ImageOps.invert(img_bin.convert('L'))
      img_arr.show()

      rectangleCoordinates, componentNum, values = findCoordinates(
         arr_labeled_img)  # this finds the coordinates of the rectangles.

      result_img, letters, letter_coords = identifyCharactersDrawRectangles(rectangleCoordinates, componentNum, img_blobs, img_color, values)

      img_name = os.path.basename(path)
      img_name = os.path.splitext(img_name)[0]

      curr_direc = curr_dir + f"\output_img_{img_name}.jpg"


      #save the image
      result_img = result_img.convert('RGB')
      try:
         result_img.save(curr_direc, "JPEG", quality=80, optimize=True, progressive=True)
      except IOError:
         ImageFile.MAXBLOCK = result_img.size[0] * result_img.size[1]
         result_img.save(curr_direc, "JPEG", quality=80, optimize=True, progressive=True)

      #notify the user
      pya.alert("There are "+ str(num_labels) +" objects in the input image.\n\n'A' count: "+str(letters[0])+"\n\n'B' count: "+str(letters[1])+ "\n\n"+
                "'C' count: "+ str(letters[2])+"\n\n '1' count: "+ str(letters[3]), programTitle)
      file_str = "\n\nmin_x, min_y, max_x, max_y\n"
      ctr = 0
      for v in letter_coords:
         file_str = file_str +"\n"+ v + ": " + str(rectangleCoordinates[ctr])
         ctr += 1
      file_str += "\n\n"


      filename = curr_dir + f"\output_text_{img_name}.txt"
      output_file = open(filename, "w+")
      output_file.write("Image Name: "+img_name+"\n\nThere are "+ str(num_labels) +" objects in the input image.\n\n'A' count: "+str(letters[0])+"\n\n'B' count: "+str(letters[1])+ "\n\n"+
                "'C' count: "+ str(letters[2])+"\n\n'1' count: "+ str(letters[3])+file_str+"\n")

      output_file.close()



def GUI(programTitle, curr_dir):
   path = "null"
   root = Tk()
   root.withdraw() #to hide tkinter window

   isBrowse = pya.confirm("Please choose an image file. \n\nPNG files may be result in wrong outputs since they have transparent background.\n\nPlease choose a JPG file.",
                          programTitle, buttons=["Browse", "Exit"])

   isFileSelected = False
   if isBrowse == "Browse":
      currdir = os.getcwd()
      path = filedialog.askopenfile()
      path = str(path)
      path = path[25:-29]
      isFileSelected = True

   else:
      pya.alert("Output image(s) and text file(s) are saved at:\n" + curr_dir, title=programTitle)
      sys.exit("You choosed to exit the program.")

   threshold_value = pya.prompt(text='Enter the threshold value.\n\nClick "OK" if you want to set it to default.\n\nDefault value works well with sample images.',
                                title=programTitle, default='155')
   if threshold_value is None:
      sys.exit("Click OK to set threshold to default!!!!!!!!")

   if isFileSelected:
      isBegin = pya.confirm("Image Path: "+path+"\n\nPress start to execute the program.", programTitle,
                            buttons=["Start", "Exit"])
      if isBegin == "Start":
         print("Program started succesfully.")
      else:
         pya.alert("Output image(s) and text file(s) are saved at:\n" + curr_dir, title=programTitle)

         sys.exit("You choosed to exit the program.")
   else:
      pya.alert("You need to choose an image!")
      sys.exit("You need to choose an image!")

   return path, int(threshold_value)


def identifyCharactersDrawRectangles(rectangleCoordinates, componentNum, img_blobs, img_color, values):
   a_count, b_count, c_count, count_1 = 0, 0, 0, 0
   letter_coords = []
   letters = []
   font = ImageFont.truetype("arial.ttf", 30)
   THRESH, ZERO, ONE = 200, 0, 255
   counter = 0
   draw = ImageDraw.Draw(img_color)  # draw rectangles at given image
   for i in range(0, componentNum):
      # min yx, max yx
      draw.rectangle((rectangleCoordinates[i][0], rectangleCoordinates[i][1], rectangleCoordinates[i][2],
                      rectangleCoordinates[i][3]), outline="red", width=2)

      cropimg = img_blobs.crop(
         (rectangleCoordinates[counter][0] - 3, rectangleCoordinates[counter][1] - 3,
          rectangleCoordinates[counter][2] + 3,
          rectangleCoordinates[counter][3] + 3))  # crop the letter

      #cropimg.show() #show the steps

      crop_img_copy = cropimg.copy()
      converted_crop = crop_img_copy.convert('L')
      cropimg_arr = np.asarray(converted_crop)
      cropimg_arr_copy = cropimg_arr.copy()

      n_rows, n_cols = cropimg_arr_copy.shape
      #print(i, n_rows, n_cols)
      currentValues = []
      overlap = False

      for k in range(0, n_rows):
         for j in range(0, n_cols):
            if cropimg_arr_copy[k][j] != 0:
               if cropimg_arr_copy[k][j] not in currentValues:
                  currentValues.append(cropimg_arr_copy[k][j])
      #print(currentValues)
      if len(currentValues) != 1:
         overlap = True
         print("Overlap detected!")
      if overlap == True:
         for k in range(0, n_rows):
            for j in range(0, n_cols):
               if cropimg_arr_copy[k][j] != currentValues[0]:
                  cropimg_arr_copy[k][j] = 0
      counter += 1
      cropimg_img = Image.fromarray(cropimg_arr_copy)
      img_inverted = ImageOps.invert(cropimg_img.convert('L'))
      #img_inverted.show() #show steps
      img_inverted_arr = np.asarray(img_inverted)

      bin_arr = threshold(img_inverted_arr, THRESH, ONE, ZERO)
      inverted_sh_img = Image.fromarray(bin_arr)
      #inverted_sh_img.show() #show steps
      invert_again = ImageOps.invert(inverted_sh_img.convert('L'))
      #invert_again.show() #show steps
      invert_again_arr = np.asarray(invert_again)

      arr_labeled_img, num_labels, arr_blobs1 = blob_coloring_8_connected(invert_again_arr, ONE)
      img_blobs1 = Image.fromarray(arr_blobs1)
      #img_blobs1.show()  # show the labelled cropped image
      c_or_1 = False
      charac = " "
      if num_labels - 1 == 1:
         charac = "A"
         a_count += 1
         letter_coords.append(charac)
      if num_labels - 1 == 2:
         charac = "B"
         b_count += 1
         letter_coords.append(charac)
      if num_labels - 1 == 0:
         c_or_1 = True
         if c_or_1:
            row, col = invert_again_arr.shape
            row -= 1
            col -= 1
            row = row // 2
            col = col // 2
            if invert_again_arr[row][col] == 0:
               charac = "1"
               count_1 += 1
            else:
               charac = "C"
               c_count += 1
         letter_coords.append(charac)

      draw.text((rectangleCoordinates[i][0] + 7, rectangleCoordinates[i][1] - 27), charac, fill="red", font=font)
   letters = [a_count, b_count, c_count, count_1]
   img_color.show() #show the results ############ tab inside into the loop to show the steps one by one ###########
   return img_color, letters, letter_coords

def findCoordinates(im):
   print("Finding rectangle coordinates, it will take a while. Please Wait...")
   counter = 0
   values = []
   temp = []
   coords = []
   nrow, ncol = im.shape
   min_x = 9999999
   min_y = 9999999
   max_x = -9999999
   max_y = -9999999
   for i in range(0, nrow):  # find the labels of the components
      for j in range(0, ncol):
         if im[i][j] != 0:
            if im[i][j] not in values:
               values.append(im[i][j])
               counter += 1
   for k in values:
      for i in range(0, nrow):
         for j in range(0, ncol):
            if im[i][j] == k:
               if i < min_x:
                  min_x = i
               if i > max_x:
                  max_x = i
               if j < min_y:
                  min_y = j
               if j > max_y:
                  max_y = j
      temp.append(min_y)
      temp.append(min_x)
      temp.append(max_y)
      temp.append(max_x)
      coords.append(temp)
      temp = []
      min_x = 9999999
      min_y = 9999999
      max_x = -9999999
      max_y = -9999999

   return coords, counter, values
# BINARIZATION
#-----------------------------------------------------------------------------------
# Function for creating and returning a binary image as a numpy array by thresholding 
# the given array of the grayscale image
def threshold(arr_gray_in, T, LOW, HIGH):
   # get the numbers of rows and columns in the array of the grayscale image
   n_rows, n_cols = arr_gray_in.shape
   # initialize the output (binary) array by using the same size as the input array
   # and filling with zeros
   arr_bin_out = np.zeros(shape = arr_gray_in.shape)
   # for each value in the given array of the grayscale image
   for i in range(n_rows):
      for j in range(n_cols):
         # if the value is smaller than the given threshold T
         if abs(arr_gray_in[i][j]) < T:
            # the corresponding value in the output (binary) array becomes LOW
            arr_bin_out[i][j] = LOW
         # if the value is greter than or equal to the given threshold T
         else:
            # the corresponding value in the output (binary) array becomes HIGH
            arr_bin_out[i][j] = HIGH
   # return the resulting output (binary) array
   return arr_bin_out

# CONNECTED COMPONENT LABELING AND BLOB COLORING
#-----------------------------------------------------------------------------------
# Function for labeling objects as 8-connected components in a binary image whose
# numpy array is given as an input argument and creating an image with randomly 
# colored components (blobs)
def blob_coloring_8_connected(arr_bin, ONE):
   print("Finding and coloring the blobs.")
   # get the numbers of rows and columns in the array of the binary image
   n_rows, n_cols = arr_bin.shape
   # max possible label value is set as 10000
   max_label = 10000
   # initially all the pixels in the image are labeled as max_label
   arr_labeled_img = np.zeros(shape = (n_rows, n_cols), dtype = int)
   for i in range(n_rows):
      for j in range(n_cols):
         arr_labeled_img[i][j] = max_label
   # keep track of equivalent labels in an array
   # initially this array contains values from 0 to max_label - 1
   equivalent_labels = np.arange(max_label, dtype = int)
   # labeling starts with k = 1
   k = 1
   # first pass to assign initial labels and update equivalent labels from conflicts
   # for each pixel in the binary image
   #--------------------------------------------------------------------------------
   for i in range(1, n_rows - 1):
      for j in range(1, n_cols - 1):
         c = arr_bin[i][j] # value of the current (center) pixel
         l = arr_bin[i][j - 1] # value of the left pixel
         label_l  = arr_labeled_img[i][j - 1] # label of the left pixel
         u = arr_bin[i - 1][j] # value of the upper pixel
         label_u  = arr_labeled_img[i - 1][j] # label of the upper pixel
         ul = arr_bin[i - 1][j - 1]
         label_ul = arr_labeled_img[i - 1][j - 1]
         ur = arr_bin[i - 1][j + 1]
         label_ur = arr_labeled_img[i - 1][j + 1]
         # only the non-background pixels are labeled
         if c == ONE:
            # get the minimum of the labels of the upper and left pixels
            min_label = min(label_u, label_l, label_ur, label_ul)
            # if both upper and left pixels are background pixels
            if min_label == max_label:
               # label the current (center) pixel with k and increase k by 1
               arr_labeled_img[i][j] = k
               k += 1
            # if at least one of upper and left pixels is not a background pixel
            else:
               # label the current (center) pixel with min_label
               arr_labeled_img[i][j] = min_label
               # if upper pixel has a bigger label and it is not a background pixel
               if min_label != label_u and label_u != max_label:
                  # update the array of equivalent labels for label_u
                  update_array(equivalent_labels, min_label, label_u)
               # if left pixel has a bigger label and it is not a background pixel
               if min_label != label_l and label_l != max_label:
                  # update the array of equivalent labels for label_l
                  update_array(equivalent_labels, min_label, label_l)
               if min_label != label_ul and label_ul != max_label:
                  update_array(equivalent_labels, min_label, label_ul)

               if min_label != label_ur and label_ur != max_label:
                  update_array(equivalent_labels, min_label, label_ur)
   # final reduction in the array of equivalent labels to obtain the min. equivalent
   # label for each used label (values from 1 to k - 1) in the first pass of labeling
   #--------------------------------------------------------------------------------
   for i in range(1, k):
      index = i
      while equivalent_labels[index] != index:
         index = equivalent_labels[index]
      equivalent_labels[i] = equivalent_labels[index]
   # rearrange equivalent labels so they all have consecutive values starting from 1
   # using the rearrange_array function which also returns the number of different
   # values of the labels used to label the image
   num_different_labels = rearrange_array(equivalent_labels, k)
   # create a color map for randomly coloring connected components (blobs)
   #--------------------------------------------------------------------------------
   color_map = np.zeros(shape = (k, 3), dtype = np.uint8)
   np.random.seed(0)
   for i in range(k):
      color_map[i][0] = np.random.randint(0, 255, 1, dtype = np.uint8)
      color_map[i][1] = np.random.randint(0, 255, 1, dtype = np.uint8)
      color_map[i][2] = np.random.randint(0, 255, 1, dtype = np.uint8)
   # create an array for the image to store randomly colored blobs
   arr_color_img = np.zeros(shape = (n_rows, n_cols, 3), dtype = np.uint8)
   # second pass to resolve labels by assigning the minimum equivalent label for each
   # label in arr_labeled_img and color connected components (blobs) randomly
   #--------------------------------------------------------------------------------
   for i in range(n_rows):
      for j in range(n_cols):
         # only the non-background pixels are taken into account and the pixels
         # on the boundaries of the image are always labeled as 0
         if arr_bin[i][j] == ONE and arr_labeled_img[i][j] != max_label:
            arr_labeled_img[i][j] = equivalent_labels[arr_labeled_img[i][j]]
            arr_color_img[i][j][0] = color_map[arr_labeled_img[i][j], 0]
            arr_color_img[i][j][1] = color_map[arr_labeled_img[i][j], 1]
            arr_color_img[i][j][2] = color_map[arr_labeled_img[i][j], 2]
         # change the label values of background pixels from max_label to 0
         else:
            arr_labeled_img[i][j] = 0
   # return the labeled image as a numpy array, number of different labels and the
   # image with colored blobs (components) as a numpy array
   return arr_labeled_img, num_different_labels, arr_color_img

# Function for updating the equivalent labels array by merging label1 and label2
# that are determined to be equivalent
def update_array(equ_labels, label1, label2) :
   # determine the small and large labels between label1 and label2
   if label1 < label2:
      lab_small = label1
      lab_large = label2
   else:
      lab_small = label2
      lab_large = label1
   # starting index is the large label
   index = lab_large
   # using an infinite while loop
   while True:
      # update the label of the currently indexed array element with lab_small when
      # it is bigger than lab_small
      if equ_labels[index] > lab_small:
         lab_large = equ_labels[index]
         equ_labels[index] = lab_small
         # continue the update operation from the newly encountered lab_large
         index = lab_large
      # update lab_small when a smaller label value is encountered
      elif equ_labels[index] < lab_small:
         lab_large = lab_small # lab_small becomes the new lab_large
         lab_small = equ_labels[index] # smaller value becomes the new lab_small
         # continue the update operation from the new value of lab_large
         index = lab_large
      # end the loop when the currently indexed array element is equal to lab_small
      else: # equ_labels[index] == lab_small
         break

# Function for rearranging min equivalent labels so they all have consecutive values
# starting from 1
def rearrange_array(equ_labels, final_k_value):
   # find different values of equivalent labels and sort them in increasing order
   different_labels = set(equ_labels[1:final_k_value])
   different_labels_sorted = sorted(different_labels)
   # compute the number of different values of the labels used to label the image
   num_different_labels = len(different_labels)
   # create an array for storing new (consecutive) values for equivalent labels
   new_labels = np.zeros(final_k_value, dtype = int)
   count = 1 # first label value to assign
   # for each different label value (sorted in increasing order)
   for l in different_labels_sorted:
      # determine the new label
      new_labels[l] = count
      count += 1 # increase count by 1 so that new label values are consecutive
   # assign new values of each equivalent label
   for ind in range(1, final_k_value):
      old_label = equ_labels[ind]
      new_label = new_labels[old_label]
      equ_labels[ind] = new_label
   # return the number of different values of the labels used to label the image
   return num_different_labels
# main() function is specified as the entry point where the program starts running


if __name__=='__main__':
   main()