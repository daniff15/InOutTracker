import random
from simulate import *
from random import randint, choices
import time

store1_1 = Store("Zara" , 45)
store2_1 = Store("Sport Zone" , 55)
store3_1 = Store("Primark" , 70)
store4_1 = Store("Modalfa" , 35)
store1_2 = Store("Zara" , 50)
store2_2 = Store("Aucham" , 100)
store3_2 = Store("MEO" , 5)
store4_2 = Store("Breshka" , 35)
store1_3 = Store("Intimissimi" , 35)
store2_3 = Store("Pull&Bear" , 45)

mall1 = Mall("Mall 1", 5, [store1_1, store2_1, store3_1, store4_1])
mall2 = Mall("Mall 2", 350, [store1_2, store2_2, store3_2, store4_2])
mall3 = Mall("Mall 3", 250, [store1_3, store1_3])

person1 = Person("Person 1", Status.out_mall)
person2 = Person("Person 2", Status.out_mall)
person3 = Person("Person 3", Status.out_mall)
person4 = Person("Person 4", Status.out_mall)
person5 = Person("Person 5", Status.out_mall)
person6 = Person("Person 6", Status.out_mall)
person7 = Person("Person 7", Status.out_mall)
person8 = Person("Person 8", Status.out_mall)
person9 = Person("Person 9", Status.out_mall)
person10 = Person("Person 10", Status.out_mall)
person11 = Person("Person 11", Status.out_mall)
person12 = Person("Person 12", Status.out_mall)

list_people = [person1, person2,person3, person4, person5, person6, person7, person8, person9, person10, person11, person12]

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
                    person1.change_status(Status.inside_mall)
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
                    person1.change_status(Status.inside_mall)
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
                person1.change_status(Status.out_mall)

        elif choices(pop, weights)[0] == 3:
            #e condicao para verificar se respeitou o limite
            if mall1.people_inside_mall >= mall1.mall_limit:
                mall1.waiting_outside_Mall()
                person1.change_status(Status.waiting_mall)

        else:
            #e condicao para verificar se se cansou de esperar na fila
            if mall1.people_inside_mall >= mall1.mall_limit and mall1.people_waiting_mall > 0:
                mall1.stop_waiting_outside_Mall()
                person1.change_status(Status.out_mall)


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
