About
-----

This project has made the following contributions,
1. In order to reduce the delay and energy consumption of repeated task offloading and processing, a caching mechanism is introduced. The mechanism considers task request probability, task access time, task average request time, freshness and data size to decide which tasks to cache. By considering these factors, the caching mechanism can effectively optimize the selection of tasks for caching.
2. In order to further improve the performance and accuracy of the caching mechanism, a cache update strategy is proposed based on the above caching mechanism. Evaluate the effectiveness of this strategy by monitoring cache utilization and cache hit ratios over time.
3. This paper mainly studies the joint optimization model of task offloading and caching in a multi-user single MEC environment, with the goal of minimizing the total task delay and total energy consumption. The optimization problem of this model is a mixed integer nonlinear programming problem (MINLP). To solve this optimization problem, the total delay and energy consumption are defined as objective functions, and a penalty function is used to deal with the constraints. Transform the optimization problem into a fitness function, and use the particle swarm optimization algorithm to obtain the optimal joint decision.

Dependencies
------------

1. jdk-17
2. maven
3. windows10

Testing
-------

Please follow the steps given below for running tests,

1. Make sure that the global jdk-17 environment variable has been configured.
2. Make sure you can use Maven to build pom.xml dependencies.
3. Execute 'src\main\java\experiment\main.java' to test the basic experiment, and other experiments can be customized based on the parameters built by the framework.

References
----------

[1]	GUO H, LIU J. Collaborative computation offloading for multiaccess edge computing over fiber–wireless networks[J]. IEEE Transactions on Vehicular Technology, 2018, 67(5): 4514-4526.

[2]	TANG S, CHEN L, HE K, et al. Computational intelligence and deep learning for next-generation edge-enabled industrial IoT[J]. IEEE Transactions on Network Science and Engineering, 2022.

[3]	ZHAO Z, XIA J, FAN L, et al. System optimization of federated learning networks with a constrained latency[J]. IEEE Transactions on Vehicular Technology, 2021, 71(1): 1095-1100.

[4]	KHAN M A, BACCOUR E, CHKIRBENE Z, et al. A survey on mobile edge computing for video streaming: Opportunities and challenges[J]. IEEE Access, 2022.

[5]	HAO Y, CHEN M, HU L, et al. Energy efficient task caching and offloading for mobile edge computing[J]. IEEE Access, 2018, 6: 11365-11373.

[6]	YANG X, FEI Z, ZHENG J, et al. Joint multi-user computation offloading and data caching for hybrid mobile cloud/edge computing[J]. IEEE Transactions on Vehicular Technology, 2019, 68(11): 11018-11030.

[7]	SAFAVAT S, SAPAVATH N N, RAWAT D B. Recent advances in mobile edge computing and content caching[J]. Digital Communications and Networks, 2020, 6(2): 189-194.

[8]	YU S, LANGAR R, FU X, et al. Computation offloading with data caching enhancement for mobile edge computing[J]. IEEE Transactions on Vehicular Technology, 2018, 67(11): 11098-11112.
