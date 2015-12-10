#Best Practices in BEEVA

[Replace this logo] ![alt text](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

Posible structure

## Index
  * [Load data](#load_data)
  * [Work with data](#work_data)
    * [Numpy](#numpy)
    * [pandas](#pandas)
    * [SciPy](#scipy)
  * [Visualization](#visualization)
  * [Machine Learning](#ml)
    * [Scikit-learn](#scikit-learn)
    * [Shogun](#shogun)
  * [Report](#report)
  * [References](#references)




### Numpy

The main libraries for analytics in python are: 'see how split it'

````python
    import numpy as np
````
The main characteristic is array object class which is quiet similar to lists in Python, except one condition, all the elements inside must be same type (ex. float, int, str ...). Numpy is used to make mathematical operations fast and more efficient than using lists.




### pandas
1. Introduction to pandas data structures and Working with DataFrames
2. Advices
   * Item 3a
   * Item 3b
   * 

##### 1. Introduction to pandas data structures
Pandas is a Python library for data analysis which has an amount of functions for using DataFrame structures. A DataFrame structure called `df` is used for clarify all the examples contain in this part. The next code allows import the library and create an empty dataframe.

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

Groupby function is used basically to compute an aggregation (ex. Sum, mean…), split into slices or groups and perform a transformation. It returns an object called GroupBy which...

````python
Group = df.groupby(lambda s: mean(s)
````

pandas groupby is great perform some operation on each of the pieces and it is similar as `plyr` and `dplyr` packages in R language. (asociar a R de Ramiro)



````python
    merge()
````

````python
    concat()
````


### Visualization
Matplotlib, seaborn and Boken libraries are used for plotting and visualization
````python
    import matplotlib as mp
    import seaborn as sn
    import bokeh as bk
````
