import matplotlib.pyplot as plt

thread_count = [2, 4, 6, 8, 12, 16]

cpu = [1.434, 1.334, 1.347, 1.371, 1.374, 1.355]
count = [2 * el for el in 
    [1948217, 750816, 488024, 417740, 424928, 386036]
]
count_per_cpu = [2 * el for el in 
    [1358459, 562891, 362265, 304659, 309342, 284850]
]
# plt.plot(thread_count, count)

# plt.show()

thread_count = [2, 4, 6, 8, 12, 16]

cpu = [1.406, 2.634, 2.527, 2.565, 2.599, 2.621]
count = [2 * el for el in 
    [1852543, 1176256, 1044186, 1066174, 1075619, 1080195]
]
count_per_cpu = [2 * el for el in 
    [1317156, 446594, 413196, 415696, 413808, 412189]
]
plt.plot(thread_count, count_per_cpu)

plt.show()
