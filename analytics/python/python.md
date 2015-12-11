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


### Load data

````python
    df = read_table('document.txt',sep='\s+', index_col=None)
````


### Numpy

The main libraries for analytics in python are: 'see how split it'

````python
    import numpy as np
````
The main characteristic is array object class which is quite similar to lists in Python, except one condition, all the elements inside must be same type (ex. float, int, str ...). Numpy is used to make mathematical operations fast and more efficient than using lists.




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
