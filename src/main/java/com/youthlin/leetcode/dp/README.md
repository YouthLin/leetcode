# 动态规划

https://labuladong.gitbook.io/algo/dong-tai-gui-hua-xi-lie/dong-tai-gui-hua-xiang-jie-jin-jie

求最值；
穷举；
重叠子问题；
最优子结构；【子问题相互独立】
状态转移方程；
状态压缩优化

状态转移方程：
- 明确 base case；
- 明确状态；
- 明确选择；
- 定义 dp 数组含义

```
dp[0][0][...] = base case
for(状态1所有取值)
    for(状态2所有取值)
        for(...)
            dp[状态1][状态2][...] = 最值(选择1,选择2,...)
```
