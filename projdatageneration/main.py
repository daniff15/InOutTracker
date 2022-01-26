
import random
import requests
from simulate import *
from random import randint, choices, choice
from threading import Thread
import time
from copy import deepcopy
import json
import pulsar
import sys
from numpy import ma
import datetime

try:
    client = pulsar.Client('pulsar://pulsarclient:6650')
    producer = client.create_producer(topic = 'persistent://public/default/ns1/people-count')
    consumer = client.subscribe('persistent://public/default/ns1/updates', 'updates')
except requests.exceptions.ConnectionError:
    print("Broker is not Running")
    exit(1)
serviceURL = 'springbootapi:8000/'
def populate_db():
    try:
        requests.post(f'http://{serviceURL}api/v1/users', json = {
            "id": 1,
            "type": 1,
            "name": "Admin",
            "username": "admin",
            "email": "admin@inouttracker.com",
            "password": "adminpass",
        })
        
        requests.post(f'http://{serviceURL}api/v1/shoppings', json ={
            "id": 1,
            "name": "Forum Aveiro",
            "opening_time": "09:00",
            "closing_time": "22:00",
            "max_capacity": 1500,
            "people_count": 0, "waiting": 0
        })
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 1,"shop_id": 1,"name": "Levi's","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 15, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 2,"shop_id": 1,"name": "FNAC","opening_time": "10:00", "closing_time": "22:00", "max_capacity": 50, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 3,"shop_id": 1,"name": "Sport Zone","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 155, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 4,"shop_id": 1,"name": "Zara","opening_time": "10:00", "closing_time": "22:00", "max_capacity": 140, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 5,"shop_id": 1,"name": "Pull & Bear", "opening_time": "09:00", "closing_time": "21:00", "max_capacity": 36, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 6,"shop_id": 1,"name": "Bershka","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 36, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 7,"shop_id": 1,"name": "Springfield","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 40, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 8,"shop_id": 1,"name": "Claire's","opening_time": "10:00", "closing_time": "20:00", "max_capacity": 14, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 9,"shop_id": 1,"name": "Body Shop","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 6, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 10,"shop_id": 1,"name": "Bimba Y Lola", "opening_time": "09:00", "closing_time": "22:00", "max_capacity": 15, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 11,"shop_id": 1,"name": "Boutique dos Relógios","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 8, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 12,"shop_id": 1,"name": "Calzedonia","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 12, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 13,"shop_id": 1,"name": "Decenio","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 20, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 14,"shop_id": 1,"name": "Quebramar","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 28, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 15,"shop_id": 1,"name": "Tiffosi","opening_time": "09:00", "closing_time": "22:00", "max_capacity": 40, "people_count": 0, "waiting": 0})

        requests.post(f'http://{serviceURL}api/v1/shoppings', json = {
            "id": 2,
            "name": "Glicinias Plaza",
            "opening_time": "09:00",
            "closing_time": "22:00",
            "max_capacity": 1200,
            "people_count": 0, "waiting": 0
        })
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 16,"shop_id": 2,"name": "Mi Store","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 15, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 17,"shop_id": 2,"name": "Worten","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 100, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 18,"shop_id": 2,"name": "Sport Zone","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 80, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 19,"shop_id": 2,"name": "Auchan","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 250, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 20,"shop_id": 2,"name": "C&A","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 80, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 21,"shop_id": 2,"name": "H&M","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 80, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 22,"shop_id": 2,"name": "Cortefiel","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 60, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 23,"shop_id": 2,"name": "Lefties","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 80, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 24,"shop_id": 2,"name": "New Yorker","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 50, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 25,"shop_id": 2,"name": "Springfield","opening_time": "09:00", "closing_time": "21:00", "max_capacity": 40, "people_count": 0, "waiting": 0})
   
        requests.post(f'http://{serviceURL}api/v1/shoppings', json = {
            "id": 3,
            "name": "Shopping Noturno",
            "opening_time": "00:00",
            "closing_time": "09:00",
            "max_capacity": 300,
            "people_count": 0, "waiting": 0
        })
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 26,"shop_id": 3,"name": "Auchan","opening_time": "00:00", "closing_time": "09:00", "max_capacity": 250, "people_count": 0, "waiting": 0})
        requests.post(f'http://{serviceURL}api/v1/stores', json = {"id": 27,"shop_id": 3,"name": "FNAC","opening_time": "00:00", "closing_time": "09:00", "max_capacity": 50, "people_count": 0, "waiting": 0})

    except requests.exceptions.ConnectionError:
        print("Server is not running")
        exit(1)

def fetchDBInfo(): 
    try: 
        response_malls = requests.get(f'http://{serviceURL}api/v1/shoppings')
        response_malls = response_malls.json()
    except requests.exceptions.ConnectionError:
        print("Server is not running")
        exit(1)

    malls = []
    for mall in response_malls:  
        stores = []
        for store in mall['stores']:
            stores.append(Store(store['id'], store['name'], store['max_capacity'], store['opening_time'], store['closing_time']))
        malls.append(Mall(mall['id'], mall["name"], mall['max_capacity'], stores, mall['opening_time'], mall['closing_time']))
    return malls

def produce(message):
    producer.send(json.dumps(message).encode('utf-8'))

def consume():
    global UPDATE
    while True:
        msg = consumer.receive()
        try:
            print("Received message '{}' id='{}'".format(msg.data(), msg.message_id()))
            UPDATE = True
            consumer.acknowledge(msg)
        except:
            print("Message failed to be processed")
            consumer.negative_acknowledge(msg)

if __name__ == '__main__':
    UPDATE = False
    thread = Thread(target = consume)
    populate_db()
    malls = fetchDBInfo()
    thread.start()

    #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall
    pop = [1, 2, 3, 4]
    weights = [0.30, 0.23, 0.23, 0.23]

    #1-respeitou 2-nao respeitou
    pop_waitLine = [1, 2]
    weights_waitLine = [0.90, 0.10]

    #Starting day
    hours_of_day = [00 , 00]
    #format 00 - h - 00
    #hours_of_day = ["00" , "00"]
    #               horas, minutos
    day = 0
    #In case the store or mall are closed, do nothing
    numbers_of_iters = 0

    day_info = {}
    hour_info = {}

    start = time.time()

    while True:
        send = False
        if UPDATE:
            malls = fetchDBInfo()
            UPDATE = False
        #Choose the mall (random)

        mall_idx = randint(0, len(malls) - 1)
        mall = malls[mall_idx]

        if len(mall.stores) == 0:
            continue

        if hours_of_day == [00 , 00] and numbers_of_iters == 0:
            print("NEW DAY")
            day += 1
            print("DAY - ", day)

        # primeira imp, apenas um mall e simulação em varias lojas
        #1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall
        idx_for_store = randint(0, len(mall.stores)-1)
        store = mall.stores[idx_for_store]

        if len(store.inside_store_ids) >= store.store_limit:
            #print("ENTREI PARA " , mall.mall_name)
            weights = [0.25, 0.40, 0.20, 0.15]
        else:
            weights = [0.30, 0.23, 0.23, 0.23]

        val = choices(pop, weights)[0]

        #mall or store are closed
        #if mall is closed than all stores are closed, so check first mall
        opening_mall = mall.opening_time.split(":")
        for i in range(len(opening_mall)):
            opening_mall[i] = int(opening_mall[i])

        closing_mall = mall.close_time.split(":")
        for i in range(len(closing_mall)):
            closing_mall[i] = int(closing_mall[i])

        if (opening_mall[0] > hours_of_day[0]): #shopping fechado
            val = 5
        elif (opening_mall[0] == hours_of_day[0] and opening_mall[1] > hours_of_day[1]): #shopping fechado
            val = 5
        elif (closing_mall[0] < hours_of_day[0]):
            val = 5
        elif (closing_mall[0] == hours_of_day[0] and closing_mall[1] <= hours_of_day[1]):
            val = 5


        if val == 5:
            mall.inside_mall_ids = []
            mall.waiting_mall_ids = []
            for store in mall.stores:
                if len(store.inside_store_ids) > 0:
                    send = True
                    store.inside_store_ids = []
                if len(store.waiting_store_ids) > 0:
                    store.waiting_store_ids = []
            for shopping in malls:
                if mall.close_time == shopping.close_time:
                    shopping.inside_mall_ids = []
                    shopping.waiting_mall_ids = []
                    for store in shopping.stores:
                        if len(store.inside_store_ids) > 0:
                            store.inside_store_ids = []
                        if len(store.waiting_store_ids) > 0:
                            store.waiting_store_ids = []
        #print("1-enter_mall; 2-exit_mall; 3-wait_mall; 4-no_wait_mall")
        #print("VAL - ", val)
        #print("WEIGHTS - ", weights)
        #print("LOJA ESCOLHIDA - ", store.store_name)

        if val == 1:
            # e condicao para verificar se respeitou o limite
            if len(mall.inside_mall_ids) < mall.mall_limit:
                mall.enterMall(mall.people_id)
                #Condicao que verifica se a pessoa respeitou a fila de espera
                respeitou_int = choices(pop_waitLine, weights_waitLine)[0]
                if respeitou_int == 1:
                    respeitou = True
                else:
                    respeitou = False
                if len(store.inside_store_ids) >= store.store_limit and respeitou:
                    store.waiting_outside_Store(mall.people_id)
                else:
                    #!Supostamente ja entra quando nao respeita
                    store.enterStore(mall.people_id)
                    if store.id in hour_info:
                        hour_info[store.id] += 1
                    else:
                        hour_info[store.id] = 1
                mall.people_id += 1

        elif val == 2:
            if len(mall.inside_mall_ids) > 0:
                idx = randint(0 , len(mall.inside_mall_ids) - 1)
                person_id = mall.inside_mall_ids[idx]
                mall.exitMall(idx)
                #remove person from store if its in one
                for loja in mall.stores:
                    if person_id in loja.inside_store_ids:
                        idx_to_remove = loja.inside_store_ids.index(person_id)
                        loja.exitStore(idx_to_remove)
                        if len(loja.waiting_store_ids) > 0:
                            loja.waiting_list_to_inside_store()
                #remove person from waiting list of store if the person leaves the mall 
                for loja in mall.stores:
                    if person_id in loja.waiting_store_ids:
                        idx_to_remove = loja.waiting_store_ids.index(person_id)
                        loja.stop_waiting_store(idx_to_remove)
                if len(mall.waiting_mall_ids) > 0:
                    val = mall.waiting_list_to_inside_mall()

                    if len(store.inside_store_ids) >= store.store_limit: #and respeitou:
                        store.waiting_outside_Store(val)
                    else:
                        store.enterStore(val)
                        mall.people_id += 1 

        elif val == 3:
            # e condicao para verificar se respeitou o limite
            respeitou_int = choices(pop_waitLine, weights_waitLine)[0]

            if respeitou_int == 1:
                respeitou = True
            else:
                respeitou = False

            if len(mall.inside_mall_ids) >= mall.mall_limit and respeitou:
                mall.waiting_outside_Mall(mall.people_id)
                mall.people_id += 1
            elif len(mall.inside_mall_ids) >= mall.mall_limit and not respeitou:
                mall.enterMall(mall.people_id)

                #nao respeitou e agora tem de entrar numa loja
                respeitou_int = choices(pop_waitLine, weights_waitLine)[0]

                if respeitou_int == 1:
                    respeitou = True
                else:
                    respeitou = False

                if len(store.inside_store_ids) >= store.store_limit and respeitou:
                    store.waiting_outside_Store(mall.people_id)
                else:
                    #!Supostamente ja entra quando nao respeita
                    store.enterStore(mall.people_id)
                mall.people_id += 1

        elif val == 4:
            #e condicao para verificar se se cansou de esperar na fila
            if len(mall.inside_mall_ids) >= mall.mall_limit and len(mall.waiting_mall_ids) > 0:
                idx_temp = randint(0 , len(mall.waiting_mall_ids) - 1)
                mall.stop_waiting_outside_Mall(idx_temp)
            
        else: #val == 5 #store or mall are closed
            #just pass the time
            pass
            
        #To see when to increase the hours_of_day    
        numbers_of_iters += 1

        stores_capacity = {
            'shoppings': {
                mall.id: len(mall.inside_mall_ids)
            },
            'stores': {},
            'waiting_stores': {},
            'daily_info': {}
        }
        if (time.time() - start > 1):
            start = time.time()
            for store in mall.stores:
                inside = len(store.inside_store_ids)
                waiting = len(store.waiting_store_ids)
                stores_capacity.get('stores')[store.id] = inside
                stores_capacity.get('waiting_stores')[store.id] = waiting
                if (not send):
                    send = True if inside + waiting > 0 else False

        
        if (send):
            produce(stores_capacity)

        
        for shopping in malls:
            print("--------------START--------------")
            print("SHOPPING ESCOLHIDO - ", shopping.mall_name)
            print("Inside mall - ", shopping.inside_mall_ids)
            print("Waiting mall - ", shopping.waiting_mall_ids)
            print("LEN - ", len(shopping.inside_mall_ids))
            for store in shopping.stores:
                print("INSIDE  ", store.store_name , " - " , store.inside_store_ids)           
                print("WAITING ", store.store_name, " - ", store.waiting_store_ids)
                print("LEN - ", len(store.inside_store_ids))
            print("--------------END--------------")
        
        #JUST TO TEST INCREASE ON HOURS_OF_DAY
        if numbers_of_iters == 200:
            numbers_of_iters = 0
            if hours_of_day == [23 , 45]:
                #End of the day
                stores_capacity['daily_info'] = day_info
                produce(stores_capacity)
                hours_of_day = [00 , 00]
                day_info = {}
            elif hours_of_day[1] != 45:
                hours_of_day[1] += 15
            elif hours_of_day[1] == 45:
                #End of an hour
                day_info[hours_of_day[0]] = hour_info
                hour_info = {}
                hours_of_day[0] += 1
                hours_of_day[1] = 00

        print("DAILY INFO  - " , day_info)
        print("-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|")
        
        time.sleep(0.025)
