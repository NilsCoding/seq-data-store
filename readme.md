# seq-data-store *by NilsCoding*

A low-level library to store and read sequencial data including their data types. No schema required. Define the order of the data in your program.

# class: BinaryOutput

Give it an OutputStream and use the various write methods to store the data. For each data a marker byte (and a length info if needed) is stored, followed by the data itself.

# class: BinaryInput

Give it an InputStream and call the read method as long as it does not return null. You will get a BinaryInputData object which contains the data and also the marker bytes, which you can compare to the MagicMarker bytes.

# tipps and gimmicks

You can use marker masks to only read specific data types.

The output data is neither encrypted nor compressed. If you need such functions, you must add them at a higher program level.

# further reading

I've written a more detailled article (in german) which can be found [here](http://www.nilscoding.com/12_16/seq-data-store.html).

# Copyright / License

*seq-data-store* is licensed under the MIT License

## The MIT License (MIT)

Copyright (c) 2016 Nils

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE. 


