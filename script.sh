#!/bin/bash
echo $('./gensort -a 33500000 3GB_file.txt')
echo $(./gensort -a 33500000 3GB_file.txt) 
sleep 10
echo $('ls -sh')
echo $(ls -sh) 
sleep 10
echo $('time LC_ALL=C sort -k1 3GB_file.txt -o 3GBS.txt --parallel=`nproc`')
echo $(time LC_ALL=C sort -k1 3GB_file.txt -o 3GBS.txt --parallel=`nproc`) 
sleep 10
echo $('./valsort 3GBS.txt')
echo $(./valsort 3GBS.txt) 
sleep 10
echo $('./gensort -a 64000000 6GB_file.txt')
echo $(./gensort -a 64000000 6GB_file.txt) 
sleep 10
echo $('ls -sh')
echo $(ls -sh) 
sleep 10
echo $('time LC_ALL=C sort -k1 6GB_file.txt -o 6GBS.txt --parallel=`nproc`')
echo $(time LC_ALL=C sort -k1 6GB_file.txt -o 6GBS.txt --parallel=`nproc`) 
sleep 10
echo $('./valsort 6GBS.txt')
echo $(./valsort 6GBS.txt) 
sleep 10
echo $('./gensort -a 125000000 12GB_file.txt')
echo $(./gensort -a 125000000 12GB_file.txt) 
sleep 10
echo $('ls -sh')
echo $(ls -sh) 
sleep 10
echo $('time LC_ALL=C sort -k1 12GB_file.txt -S6G -o 12GBS.txt --parallel=`nproc`')
echo $(time LC_ALL=C sort -k1 12GB_file.txt -S6G -o 12GBS.txt --parallel=`nproc`) 
sleep 10
echo $('./valsort 12GBS.txt')
echo $(./valsort 12GBS.txt) 
sleep 10