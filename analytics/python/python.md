#Best Practices in BEEVA

[Replace this logo] ![alt text](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

## Index

* [Numpy](#numpy)
* [pandas](#pandas)
* [SciPy](#scipy)
* [Scikit-learn](#scikit-learn)
* [Visualization](#visualization)
* [References](#references)

### Numpy


### Visualization
Matplotlib, seaborn and Boken libraries are used for plotting and visualization


The main libraries for analytics in python are:

### pandas
1. Introduction to pandas data structures and Working with DataFrames
2. Advices
   * Item 3a
   * Item 3b
   * 

##### 1. Introduction to pandas data structures
Pandas is a Python library for data analysis which has an amount of functions for using DataFrame structures. A DataFrame structure called df is used for clarify all the examples contain in this part.

````python
    import pandas as pd
    df = pd.DataFrame()
````

**How get information from a DataFrame?**
It is useful for extract and get information from your DataFrame, for example with the functions `df.info` and `df.describe`.The second one also provides a brief statistical description about your dataset for example the mean, standard deviation, maximum values and percentiles…

A really good function in order to check all the types which compose your DataFrame is `df.dtypes`.

A quickly way to see the first and the last records is use `df.head(N)` and `df.tail(N)` respectively, where N is the number of records that you want to check.

**How select a certain field or slicing a DataFrame?**
The easy way to select a column or field in a DataFrame is using the notation `df[‘name’]`. A great thing is use previous function in order to get information just for this column as for example `df[‘name’].describe()` and `df[‘name’].dtypes`. Several columns can be selected with an additional bracket as `df[[‘name1’, ‘name2’]]`.
Groupby function is used basically to compute an aggregation (ex. Sum, mean…), split into slices or groups and perform a transformation. It returns an object called GroupBy which is 
Group = df.groupby(lambda s: mean(s)


