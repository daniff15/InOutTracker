import random
import requests
from simulate import *
from random import randint, choices
import time
from copy import deepcopy
import json
import pulsar

client = pulsar.Client('pulsar://localhost:6650')
producer = client.create_producer(topic = 'persistent://public/default/ns1/people-count')

try:
    response_malls = requests.get('http://localhost:8000/api/v1/shoppings')
    response_stores = requests.get('http://localhost:8000/api/v1/stores')
    response_malls = response_malls.json()
    response_stores = response_stores.json()
except requests.exceptions.ConnectionError:
    print("Server is not running")
    exit(1)

stores = []
for store in response_stores:
    stores.append(Store(store['id'], store['name'], store['max_capacity'], store['opening_time'], store['closing_time']))

for mall in response_malls:  
    mall1 = Mall(mall['id'], mall["name"], mall['max_capacity'], stores, mall['opening_time'], mall['closing_time'])

else:
    mall1 = Mall(0, "Forum Aveiro", 50, stores, "08h00", "20h00")


def produce(message):
    producer.send(json.dumps(message).encode('utf-8'))

if __name__ == '__main__':

    pop = [1, 2, 3, 4]
    weights = [0.30, 0.23, 0.23, 0.23]
    id = 0

    while True:
        # primeira imp, apenas um mall e simulação em varias lojas
        #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall

        idx_for_store = randint(0, len(mall1.stores)-1)
        store = mall1.stores[idx_for_store]
        print("LOJA ESCOLHIDA - ", store.store_name) 

        if choices(pop, weights)[0] == 1:
            # e condicao para verificar se respeitou o limite
            if len(mall1.inside_mall_ids) < mall1.mall_limit:
                if len(mall1.waiting_mall_ids) > 0:
                    mall1.waiting_list_to_inside_mall()

                    #TODO: METER AS CONDICOES DO Q A PESSOA VAI FAZER QUANDO ENTRAR NO SHOPPING
                    if len(store.inside_store_ids) >= store.store_limit: #and RESPEITOU:
                        store.waiting_outside_Store(id)
                    else:
                        store.enterStore(id) 
                    id += 1

                else:
                    mall1.enterMall(id)

                    #TODO: METER AS CONDICOES DO Q A PESSOA VAI FAZER QUANDO ENTRAR NO SHOPPING
                    if len(store.inside_store_ids) >= store.store_limit: #and RESPEITOU:
                        store.waiting_outside_Store(id)
                    else:
                        store.enterStore(id)

                    id += 1

        elif choices(pop, weights)[0] == 2:
            if len(mall1.inside_mall_ids) > 0:
                idx = randint(0 , len(mall1.inside_mall_ids) - 1)
                print("idx - " , idx)
                person_id = mall1.inside_mall_ids[idx]
                mall1.exitMall(idx)

                #remove person from store if its in one
                for loja in mall1.stores:
                    if person_id in loja.inside_store_ids:
                        idx_to_remove = loja.inside_store_ids.index(person_id)
                        loja.exitStore(idx_to_remove)
                        if len(loja.waiting_store_ids) > 0:
                            loja.waiting_list_to_inside_store()

                #remove person from waiting list of store if the person leaves the mall 
                for loja in mall1.stores:
                    if person_id in loja.waiting_store_ids:
                        idx_to_remove = loja.waiting_store_ids.index(person_id)
                        loja.stop_waiting_store(idx_to_remove)

                if len(mall1.waiting_mall_ids) > 0:
                    mall1.waiting_list_to_inside_mall()

        elif choices(pop, weights)[0] == 3:
            # e condicao para verificar se respeitou o limite
            if len(mall1.inside_mall_ids) >= mall1.mall_limit:
                mall1.waiting_outside_Mall(id)
                id += 1

        else:
            #e condicao para verificar se se cansou de esperar na fila
            if len(mall1.inside_mall_ids) >= mall1.mall_limit and len(mall1.waiting_mall_ids) > 0:
                idx_temp = randint(0 , len(mall1.waiting_mall_ids) - 1)
                mall1.stop_waiting_outside_Mall(idx_temp)

        ''' implementation for one mall '''
        stores_capacity = {mall1.id: len(mall1.inside_mall_ids)}
        for store in mall1.stores:
            stores_capacity[store.id] = len(store.inside_store_ids)
        produce(stores_capacity)

        #!para depois contar quantas pessoas estao em cada cena, len(lst)
        print("--------------START--------------")
        print("Inside mall - ", mall1.inside_mall_ids)
        print("Waiting mall - ", mall1.waiting_mall_ids)
        print("LEN - ", len(store.inside_store_ids))
        for store in mall1.stores:
            print("INSIDE  ", store.store_name , " - " , store.inside_store_ids)           
            print("WAITING ", store.store_name, " - ", store.waiting_store_ids)
        
        print("--------------END--------------")

        """#escolha da loja para fazer cenas randoms
        #list_idx = randint(0, len(mall1.stores))
        print(store.store_name)
        #print("Store - ", (mall1_1.stores)[1])
        exit(0)"""

        time.sleep(0.1)
#client.close()