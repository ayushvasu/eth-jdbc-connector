Ethereum Driver Performance Test
----------


**Server Configuration:-**

Geth client system configuration

  - **1. Server IP –** 172.25.41.52

  - **2. RAM –** 16 GB

  - **3. CPU CORES –** 4 cores

  - **4. Make –** Intel(R) Core(TM) i5-2400 CPU @ 3.10GHz

**Test 1**

**Condition**

- Select Query with simple range where clause only

**Test Results**

| Blocks Range | 1st Iteration (ms) | 2nd Iteration (ms) | 3rd Iteration (ms) | Avg (sec) |
| --- | --- | --- | --- | --- |
| 1 | 66 | 40 | 53 | 0.05 |
| 100 | 211 | 318 | 297 | 0.28 |
| 200 | 1489 | 858 | 1417 | 1.25 |
| 500 | 1461 | 928 | 1007 | 1.13 |
| 1000 | 1826 | 1895 | 1842 | 1.85 |
| 3000 | 9930 | 7380 | 16349 | 11.22 |
| 20000 | 65441 | 165400 | 119364 | 116.74 |

**Test 2**

**Condition**

- Select Query with range where clause and Order by Clause

**Test Results**

| Blocks Range | 1st Iteration (ms) | 2nd Iteration (ms) | 3rd Iteration (ms) | Avg (sec) |
| --- | --- | --- | --- | --- |
| 1 | 234 | 806 | 190 | 0.41 |
| 100 | 482 | 372 | 238 | 0.36 |
| 200 | 1679 | 601 | 402 | 0.89 |
| 500 | 1357 | 755 | 940 | 1.02 |
| 1000 | 1804 | 5334 | 1717 | 2.95 |
| 3000 | 11290 | 5184 | 4516 | 7.00 |
| 20000 | 123167 | 93752 | 181892 | 132.94 |

**Test 3**

**Condition**

- Select Query with range where clause and Group by Clause

**Test Results**

| Blocks Range | 1st Iteration (ms) | 2nd Iteration (ms) | 3rd Iteration (ms) | Avg (sec) |
| --- | --- | --- | --- | --- |
| 1 | 325 | 21 | 32 | 0.13 |
| 100 | 240 | 140 | 211 | 0.20 |
| 200 | 467 | 424 | 714 | 0.54 |
| 500 | 5560 | 139179 | 1280 | 48.67 |
| 1000 | 1573 | 2144 | 1647 | 1.79 |
| 3000 | 7847 | 8435 | 15888 | 10.72 |
| 20000 | 28059 | 183819 | 48958 | 86.95 |
 
--------------------
 
Ethereum Driver Performance test with stepwise time 
----------
###1- Test 1

- **Condition**

  - Select Query with simple range where clause

- **Test Results**

  - **1st Iteration**

    - **Block Range of - 1 Blocks**

                Query Execution Start at 1531127052900

                        - Dataframe - 34ms

                        - ResultSet - 0ms

                Query Execution End at 1531127052935

         Total Time - 38ms

    - **Block Range of - 100 Blocks**

                Query Execution Start at 1531127052941

                        - Dataframe - 349ms

                        - ResultSet - 0ms

                Query Execution End at 1531127053291

         Total Time - 356ms

    - **Block Range of - 200 Blocks**

                Query Execution Start at 1531127053296

                        - Dataframe - 511ms

                        - ResultSet - 0ms

                Query Execution End at 1531127053808

         Total Time - 517ms

    - **Block Range of - 500 Blocks**

                Query Execution Start at 1531127053813

                        - Dataframe - 2056ms

                        - ResultSet - 0ms

                Query Execution End at 1531127055876

         Total Time - 2068ms

    - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531127055883

                        - Dataframe - 1844ms

                        - ResultSet - 0ms

                Query Execution End at 1531127057728

         Total Time - 1852ms

    - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531127057734

                        - Dataframe - 5059ms

                        - ResultSet - 0ms

                Query Execution End at 1531127062794

         Total Time - 5065ms

    - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531127062798

                        - Dataframe - 33213ms

                        - ResultSet - 0ms

                Query Execution End at 1531127096022

         Total Time - 33228ms

   - **2nd Iteration**

     - **Block Range of - 1 Blocks**

                Query Execution Start at 1531127229976

                        - Dataframe - 43ms

                        - ResultSet - 0ms

                Query Execution End at 1531127230019

         Total Time - 45ms

      - **Block Range of - 100 Blocks**

                Query Execution Start at 1531127230023

                        - Dataframe - 183ms

                        - ResultSet - 0ms

                Query Execution End at 1531127230206

         Total Time - 187ms

      - **Block Range of - 200 Blocks**

                Query Execution Start at 1531127230210

                        - Dataframe - 391ms

                        - ResultSet - 0ms

                Query Execution End at 1531127230601

         Total Time - 395ms

     - **Block Range of - 500 Blocks**

                Query Execution Start at 1531127230606

                        - Dataframe - 855ms

                        - ResultSet - 0ms

                Query Execution End at 1531127231462

         Total Time - 861ms

     - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531127231466

                        - Dataframe - 10987ms

                        - ResultSet - 0ms

                Query Execution End at 1531127242454

         Total Time - 10992ms

     - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531127242458

                        - Dataframe - 5058ms

                        - ResultSet - 0ms

                Query Execution End at 1531127247519

         Total Time - 5065ms

     - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531127247524

                        - Dataframe - 51513ms

                        - ResultSet - 0ms

                Query Execution End at 1531127299364

         Total Time - 51845ms

  - **3rd Iteration**

    - **Block Range of - 1 Blocks**

                Query Execution Start at 1531127404732

                        - Dataframe - 23ms

                        - ResultSet - 0ms

                Query Execution End at 1531127404756

         Total Time - 27ms

    - **Block Range of - 100 Blocks**

                Query Execution Start at 1531127404761

                        - Dataframe - 766ms

                        - ResultSet - 0ms

                Query Execution End at 1531127405527

         Total Time - 771ms

    - **Block Range of - 200 Blocks**

                Query Execution Start at 1531127405535

                        - Dataframe - 342ms

                        - ResultSet - 0ms

                Query Execution End at 1531127405877

         Total Time - 350ms

    - **Block Range of - 500 Blocks**

                Query Execution Start at 1531127405883

                        - Dataframe - 863ms

                        - ResultSet - 1ms

                Query Execution End at 1531127406747

         Total Time - 869ms

    - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531127406754

                        - Dataframe - 5999ms

                        - ResultSet - 0ms

                Query Execution End at 1531127412754

         Total Time - 6007ms

    - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531127412762

                        - Dataframe - 6976ms

                        - ResultSet - 0ms

                Query Execution End at 1531127419740

         Total Time - 6986ms

    - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531127419748

                        - Dataframe - 41289ms

                        - ResultSet - 0ms

                Query Execution End at 1531127461048

         Total Time - 41308ms



###2- Test 2

- **Condition**

  - Select Query with range where clause and Order by Clause

- **Test Results**

  - **1st Iteration**

    - **Block Range of - 1 Blocks**

                Query Execution Start at 1531126901595

                        - Dataframe - 62ms

                        - order by - 2ms

                        - ResultSet - 2ms

                Query Execution End at 1531126901662

         Total Time - 236ms

    - **Block Range of - 100 Blocks**

                Query Execution Start at 1531126901678

                        - Dataframe - 264ms

                        - order by - 3ms

                        - ResultSet - 0ms

                Query Execution End at 1531126901946

         Total Time - 283ms

    - **Block Range of - 200 Blocks**

                Query Execution Start at 1531126901955

                        - Dataframe - 470ms

                        - order by - 3ms

                        - ResultSet - 0ms

                Query Execution End at 1531126902430

         Total Time - 484ms

    - **Block Range of - 500 Blocks**

                Query Execution Start at 1531126902439

                        - Dataframe - 697ms

                        - order by - 6ms

                        - ResultSet - 0ms

                Query Execution End at 1531126903146

         Total Time - 716ms

    - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531126903154

                        - Dataframe - 53964ms

                        - order by - 8ms

                        - ResultSet - 0ms

                Query Execution End at 1531126957137

         Total Time - 53991ms

    - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531126957148

                        - Dataframe - 8386ms

                        - order by - 12ms

                        - ResultSet - 1ms

                Query Execution End at 1531126965566

         Total Time - 8428ms

    - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531126965575

                        - Dataframe - 86804ms

                        - order by - 49ms

                        - ResultSet - 0ms

                Query Execution End at 1531127052896

         Total Time - 87330ms

  - **2nd Iteration**

    - **Block Range of - 1 Blocks**

                Query Execution Start at 1531127145903

                        - Dataframe - 53ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127145956

         Total Time - 57ms

    - **Block Range of - 100 Blocks**

                Query Execution Start at 1531127145964

                        - Dataframe - 305ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127146270

         Total Time - 313ms

    - **Block Range of - 200 Blocks**

                Query Execution Start at 1531127146277

                        - Dataframe - 359ms

                        - order by - 1ms

                        - ResultSet - 0ms

                Query Execution End at 1531127146637

         Total Time - 367ms

    - **Block Range of - 500 Blocks**

                Query Execution Start at 1531127146646

                        - Dataframe - 1077ms

                        - order by - 1ms

                        - ResultSet - 0ms

                Query Execution End at 1531127147724

         Total Time - 1087ms

    - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531127147730

                        - Dataframe - 1816ms

                        - order by - 2ms

                        - ResultSet - 0ms

                Query Execution End at 1531127149549

         Total Time - 1824ms

    - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531127149556

                        - Dataframe - 12435ms

                        - order by - 6ms

                        - ResultSet - 0ms

                Query Execution End at 1531127161999

         Total Time - 12450ms

    - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531127162008

                        - Dataframe - 67923ms

                        - order by - 34ms

                        - ResultSet - 0ms

                Query Execution End at 1531127229973

         Total Time - 67973ms

  - **3rd Iteration**

    - **Block Range of - 1 Blocks**

                Query Execution Start at 1531127361208

                        - Dataframe - 31ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127361239

         Total Time - 35ms

    - **Block Range of - 100 Blocks**

                Query Execution Start at 1531127361244

                        - Dataframe - 221ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127361466

         Total Time - 227ms

    - **Block Range of - 200 Blocks**

                Query Execution Start at 1531127361471

                        - Dataframe - 286ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127361758

         Total Time - 291ms

    - **Block Range of - 500 Blocks**

                Query Execution Start at 1531127361764

                        - Dataframe - 975ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127362739

         Total Time - 981ms

    - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531127362744

                        - Dataframe - 1864ms

                        - order by - 1ms

                        - ResultSet - 0ms

                Query Execution End at 1531127364610

         Total Time - 1871ms

    - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531127364615

                        - Dataframe - 5050ms

                        - order by - 5ms

                        - ResultSet - 0ms

                Query Execution End at 1531127369671

         Total Time - 5061ms

    - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531127369676

                        - Dataframe - 34808ms

                        - order by - 237ms

                        - ResultSet - 0ms

                Query Execution End at 1531127404729

         Total Time - 35058ms



###3- Test 3

- **Condition**

  - Select Query with range where clause and Group by Clause

- **Test Results**

  - **1st Iteration**

    - **Block Range of - 1 Blocks**

                Query Execution Start at 1531127096034

                        - Dataframe - 45ms

                        - group by - 5ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127096085

         Total Time - 62ms

    - **Block Range of - 100 Blocks**

                Query Execution Start at 1531127096092

                        - Dataframe - 699ms

                        - group by - 5ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127096796

         Total Time - 711ms

    - **Block Range of - 200 Blocks**

                Query Execution Start at 1531127096803

                        - Dataframe - 318ms

                        - group by - 1ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127097123

         Total Time - 327ms

    - **Block Range of - 500 Blocks**

                Query Execution Start at 1531127097133

                        - Dataframe - 6708ms

                        - group by - 18ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127103859

         Total Time - 6736ms

    - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531127103866

                        - Dataframe - 2268ms

                        - group by - 6ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127106140

         Total Time - 2281ms

    - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531127106151

                        - Dataframe - 4440ms

                        - group by - 7ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127110599

         Total Time - 4459ms

    - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531127110611

                        - Dataframe - 35246ms

                        - group by - 42ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127145899

         Total Time - 35300ms

  - **2nd Iteration**

    - **Block Range of - 1 Blocks**

                Query Execution Start at 1531127299369

                        - ResultSet - 0ms

                Query Execution End at 1531127299392

         Total Time - 28ms

    - **Block Range of - 100 Blocks**

                Query Execution Start at 1531127299399

                        - Dataframe - 368ms

                        - group by - 1ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127299768

         Total Time - 375ms

    - **Block Range of - 200 Blocks**

                Query Execution Start at 1531127299780

                        - Dataframe - 332ms

                        - group by - 0ms

                        - order by - 0ms

                        - ResultSet - 1ms

                Query Execution End at 1531127300114

         Total Time - 346ms

    - **Block Range of - 500 Blocks**

                Query Execution Start at 1531127300124

                        - Dataframe - 1030ms

                        - group by - 2ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127301156

         Total Time - 1042ms

    - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531127301168

                        - Dataframe - 5424ms

                        - group by - 7ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127306599

         Total Time - 5442ms

    - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531127306606

                        - Dataframe - 20444ms

                        - group by - 19ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127327069

         Total Time - 20470ms

    - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531127327076

                        - Dataframe - 34103ms

                        - group by - 25ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127361204

         Total Time - 34135ms

  - **3rd Iteration**

    - **Block Range of - 1 Blocks**

                Query Execution Start at 1531127461055

                        - Dataframe - 56ms

                        - group by - 0ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127461111

         Total Time - 63ms

    - **Block Range of - 100 Blocks**

                Query Execution Start at 1531127461123

                        - Dataframe - 554ms

                        - group by - 0ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127461677

         Total Time - 566ms

    - **Block Range of - 200 Blocks**

                Query Execution Start at 1531127461689

                        - Dataframe - 911ms

                        - group by - 1ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127462601

         Total Time - 923ms

    - **Block Range of - 500 Blocks**

                Query Execution Start at 1531127462614

                        - Dataframe - 861ms

                        - group by - 1ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127463476

         Total Time - 874ms

    - **Block Range of - 1000 Blocks**

                Query Execution Start at 1531127463488

                        - Dataframe - 1730ms

                        - group by - 1ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127465219

         Total Time - 1743ms

    - **Block Range of - 3000 Blocks**

                Query Execution Start at 1531127465233

                        - Dataframe - 5137ms

                        - group by - 3ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127470373

         Total Time - 5153ms

    - **Block Range of - 20000 Blocks**

                Query Execution Start at 1531127470385

                        - Dataframe - 36779ms

                        - group by - 17ms

                        - order by - 0ms

                        - ResultSet - 0ms

                Query Execution End at 1531127507181

         Total Time - 36807ms
