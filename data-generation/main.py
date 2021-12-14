import random
from simulate import *
from random import randint, choices
import time

store1_1 = Store("Zara" , 45, "10:00:00", "23:00:00")
store2_1 = Store("Sport Zone" , 55, "10:00:00", "22:00:00")
store3_1 = Store("Primark" , 70, "10:00:00", "23:00:00")
store4_1 = Store("Modalfa" , 35, "10:00:00", "22:00:00")
store1_2 = Store("Zara" , 50, "10:00:00", "22:00:00")
store2_2 = Store("Aucham" , 100, "10:00:00", "22:00:00")
store3_2 = Store("MEO" , 5, "10:00:00", "23:00:00")
store4_2 = Store("Breshka" , 35, "10:00:00", "23:00:00")
store1_3 = Store("Intimissimi" , 35, "10:00:00", "21:00:00")
store2_3 = Store("Pull&Bear" , 45, "10:00:00", "22:00:00")

mall1 = Mall("Mall 1", 5, [store1_1, store2_1, store3_1, store4_1], "9:00:00", "23:00:00")
mall2 = Mall("Mall 2", 350, [store1_2, store2_2, store3_2, store4_2], "10:00:00", "23:00:00")
mall3 = Mall("Mall 3", 250, [store1_3, store1_3], "9:30:00", "22:00:00")

if __name__ == '__main__':
    
    pop = [1,2,3,4]
    weights = [0.30, 0.23, 0.23, 0.23]
    
    while True:
        #! Escolher um shopping e fazer o q tem de fazer
        #primeira imp, apenas um mall
        #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall
        
        if choices(pop, weights)[0] == 1:
            #e condicao para verificar se respeitou o limite
            if mall1.people_inside_mall < mall1.mall_limit:
                if mall1.people_waiting_mall > 0:
                    mall1.enterMall()
                    mall1.people_waiting_mall -= 1
                    """
                    #and go shopping
                    yes_no = randint(0,1)
                    if yes_no == 1:
                        #entra na loja
                        print("entrou aqui")
                        mall1.stores[0].enterStore()
                        person1.change_status(Status.inside_store)
                    """

                else:
                    mall1.enterMall()
                    """
                    #and go shopping
                    yes_no = randint(0,1)
                    if yes_no == 1:
                        #entra na loja
                        print("entrou aqui")
                        mall1.stores[0].enterStore()
                        person1.change_status(Status.inside_store)
                    """


        elif choices(pop, weights)[0] == 2:
            if mall1.people_inside_mall > 0:
                mall1.exitMall()

        elif choices(pop, weights)[0] == 3:
            #e condicao para verificar se respeitou o limite
            if mall1.people_inside_mall >= mall1.mall_limit:
                mall1.waiting_outside_Mall()

        else:
            #e condicao para verificar se se cansou de esperar na fila
            if mall1.people_inside_mall >= mall1.mall_limit and mall1.people_waiting_mall > 0:
                mall1.stop_waiting_outside_Mall()


        #!para depois contar quantas pessoas estao em cada cena, provavlemente vai se a cada lista ver os status de cada pessoa
        print("--------------START--------------")
        print("Inside mall - " , mall1.people_inside_mall)
        print("Waiting mall - " , mall1.people_waiting_mall)
        print("INSIDE ZARA - ", mall1.stores[0].people_inside_store)
        print("--------------END--------------")

        """#escolha da loja para fazer cenas randoms
        #list_idx = randint(0, len(mall1.stores))
        print(mall1.stores[0].store_name)
        #print("Store - ", (mall1_1.stores)[1])
        exit(0)"""
        time.sleep(0.1)
