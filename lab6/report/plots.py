import matplotlib.pyplot as plt
import numpy as np

fig, ax = plt.subplots()

# Data for plotting, client cost = 100
# Monitor
# x = [1, 10, 50, 100, 200, 400, 600, 800, 1000]
# y = [1327602, 1362965, 1144082, 946010, 721321, 540101, 406930, 366733, 321637]
# ax.plot(x, y)

# ax.set(xlabel='Monitor task cost', ylabel='Tasks performed by clients')
# ax.grid()

# fig.savefig("img/synchro_100client.png")
# plt.show()

# AO
# x = [1, 10, 50, 100, 200, 400, 600, 800, 1000]
# y = [34010951, 34113771, 33337610, 33283632, 31693175, 25658791, 25781955, 26085208, 30758688]
# ax.plot(x, y)

# ax.set(xlabel='AO task cost', ylabel='Tasks performed by clients')
# ax.grid()

# fig.savefig("img/ao_100client.png")
# plt.show()

##### 2 producentow i 2 konsumentow, koszt serwera 10

# AO
x = [1, 2, 5, 10, 20, 50]
y = [14456141, 15620034, 185170, 4, 180408, 135265]
ax.plot(x, y)

ax.set(xlabel='AO task cost', ylabel='Tasks performed by clients')
ax.grid()

# plt.show()


# AO
x = [1, 2, 5, 10, 20, 50]
y = [205940, 201473, 185170, 189410, 180408, 135265]
ax.plot(x, y)

ax.set(xlabel='Monitor task cost', ylabel='Tasks performed by clients')
ax.grid()

plt.show()