# Python for Data Science

![alt text](static/python-logo.png "Python")

## Index
  * [Load data](#load_data)
  * [Work with data](#work_data)
    * [Numpy](#numpy)
    * [SciPy](#scipy)
    * [pandas](#pandas)
  * [Visualization](#visualization)


### Load data

Some examples about how load dataset from CSV, text files and urls.

````python
    import pandas as pd
    df = pd.read_csv('datos.csv')
    df = read_table('document.txt',sep='\s+', index_col=None)
````

````python
    import urllib
    url = "http://goo.gl/j0Rvxq" #Pima Indians Diabetes dataset 
    raw_data = urllib.urlopen(url)
````


### Numpy

Numpy library is extension library for Python which provides mathematical functions for problems where arrays and matrices computation is needed. Who comes from **Matlab software**, Numpy library could be a great substitute. Numpy has also the advantage that was part of python from the beginning and it contents lot of developments.  Next piece of code could be used in order to load this library:

````python
    import numpy as np
````
The main characteristic is array object class which is quite similar to lists in Python, except one condition, all the elements inside must be same type (ex. float, int, str ...). Numpy is used to make mathematical operations fast and more efficient than using lists.
For example, using next code a Numpy array (2 rows and 3 columns) is created. The function `np.shape()` is used to check the dimesion and it is useful when occurs errors as array multiplication and similar. 

````python
    X = np.array( [ [1,2,3], [4,5,6]]) 
    np.shape(X)
````
**How index and slice a numpy array?**

This could be one of the first questions when a person starts with this kind of numerical libraries. Using previous X array, the way to access to first element in the first row and its last element is shown in the next code. It could be one of the difference between python libraries and others, the first element is indexing with 0 number and not with 1.

````python
    first = X[0][0]
    last = X[0][-1]
````
As in Matlab the `eye()`function is so helpful when you want to create a 2D array with ones on the diagonal and zeros elsewhere. In almost every optimization algorithm could be used in order to reduce computational cost in all the multiplication process, diagonalization problem, resolve partial differential equations...

Numpy library has lot of useful functions when you need to work with random numbers. It is called and could be imported using `numpy.random`. An import consideration when you work with this kind of function is that you must set a certain `seed()`at the first of your code in order to get **reproducible results**. 
````python
    np.random.seed(32) # example seed is set to 32
````    
Some functions as `randn()` which use a 'standard normal' distribution, `randint` which returns random integers from a low to a high input values... Function `shuffle()`	is useful to modify a input sequence by shuffling its contents, on the other hand, `permutation()`function	randomly permute a sequence. 


### Scipy
SciPy (Scientific Python) is a Python library which is often mentioned in the same way as NumPy. SciPy extends the capabilities of NumPy with further useful functions for minimization, regression, Fourier-transformation and many others.


### pandas
This part gives a brief introduction to pandas data structures and some advices. Pandas is a Python library for data analysis which has an amount of functions for using DataFrame structures. A DataFrame structure called `df` is used for clarify all the examples contain in this part. The next code allows import the library and create an empty dataframe.

````python
    import pandas as pd
    df = pd.DataFrame()
````

A easy way to start with pandas library is loading a dataset from a csv file, returning a DataFrame structure. Next code shows how.

````python
    df= pd.read_csv('../datos.csv').fillna(" ")
````

In order to introduce this library some tipical questions are answered. 

**How get information from a DataFrame structure?**

It is useful for extract and get information from your DataFrame, for example with the functions `df.info` and `df.describe`.The second one also provides a brief statistical description about your dataset for example the mean, standard deviation, maximum values and percentiles…

A really good function in order to check all the types which compose your DataFrame structure is `df.dtypes`.

A quickly way to see the first and the last records is use `df.head(N)` and `df.tail(N)` respectively, where N is the number of records that you want to check.

**How select a certain field or slicing a DataFrame structure?**

The easy way to select a column or field in a DataFrame is using the notation `df[‘name’]`. A great thing is use previous function in order to get information just for this column as for example `df[‘name’].describe()` and `df[‘name’].dtypes`. Several columns can be selected with an additional bracket as `df[[‘name1’, ‘name2’]]`.


**How join combine and group several DataFrame structures?**

In almost every analysis, we need to merge and join datasets, usually with a specific order and relational manner. To resolve this issue pandas library contains at least 3 great functions; `groupby()`, `merge()` and `concat()`.

Groupby function is used basically to compute an aggregation (ex. Sum, mean…), split into slices or groups and perform a transformation. It returns an object called GroupBy which allows other great funcionalities. Also, it provides the ability to group by multiple columns. An example could be, grouping by columns named A and B, compute its mean value (by group): 

````python
Group = df.groupby('A','B']).mean()
````
Also useful if you want to apply multiple functions to a group and collect results. And again, `describe()`function is so useful after group and apply functions because it gives lot of information about the output. pandas-groupby functionality is great perform some operation on each of the pieces and it is similar as `plyr` and `dplyr` packages in R language. (asociar a R de Ramiro)

For SQL programmers, `merge()`function provides two DataFrames to be joined on one or more keys, using common syntax as (on, left, right, inner, outer...). For example:   

````python
    pd.merge(df1,df2, on ='key', how= 'outer')
````

This library also provides `concat()`as a way to combine DataFrame structures. It is similar to `UNION`function in SQL language. So useful when a different approach and model provides a part of the final result and you just want to combine. 

````python
    pd.concat([df1, df2])
````


### Visualization
Matplotlib, seaborn and Boken libraries are used for plotting and visualization
````python
    import matplotlib as mp
    import seaborn as sn
    import bokeh as bk
````
