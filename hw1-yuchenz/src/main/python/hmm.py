#!/usr/bin/python
# This script contains implementations of forward, backward, and 
# forward-backward algorithms
# 
# Written by Yuchen Zhang, Language Technologies Institute, CMU 

import sys
import math
import random
import pickle

INF = float('inf')    # infinity !!!
NEG_INF = float('-inf')
M_LN2 = 0.69314718055994530942
VOWELS = ['A', 'E', 'I', 'O', 'U', 'Y']

class HMM:
    # calculate log(exp(left) + exp(right)) more accurately
    # based on http://www.cs.cmu.edu/~roni/11761-s12/assignments/log_add.c
    def log_add(self, left, right):
        if (right < left):
            return left + math.log1p(math.exp(right - left))
        elif (right > left):
            return right + math.log1p(math.exp(left - right))
        else:
            return left + M_LN2

    def ln(self, value):
        if (value == 0.0):
            return NEG_INF
        else:
            return math.log(value)

    # should never use this
    def bad_decode(self, input_seq, hmm):
        (A, B) = hmm
        state_seq = list()
        t = 0
        while (t < len(input_seq)):
            ot = input_seq[t]
            curr_best_state = 0
            j = 0
            while (j < len(A)):
                if (B[j][ot] > B[curr_best_state][ot]):
                    curr_best_state = j
                j += 1
            state_seq.append(curr_best_state)
            t += 1
        return state_seq

    # hmm= (A, B)
    # This method computes the most likely sequence of states given the observation and model
    def viterbi(self, input_seq, hmm, start_state, end_state):
        (A, B) = hmm
        N = len(A)
        
        viterbi_table = list()
        bp_table = list()
        
        # calculate t=0
        start_entry = list()    
        for i in xrange(0, N):
            if (i == start_state):
                start_entry.append(ln(1.0))
            else:
                start_entry.append(ln(0.0))
        
        viterbi_table.append(start_entry)
        
        t = 1
        while (t <= len(input_seq)): # loop through time
            viterbi_t = list()
            bp_t = list()
            ot = input_seq[t - 1]   # this is t - 1 because t0 is start state
            j = 0
            while (j < N): # for each state
                i = 0
                viterbi_t_j = NEG_INF
                curr_best_node = 0
                while (i < N): # calculate alpha_t_j
                    log_sum = viterbi_table[t - 1][i] + A[i][j] + B[j][ot]
                    # alpha_t_j = log_add(alpha_t_j, log_sum)
                    if (log_sum > viterbi_t_j):
                        viterbi_t_j = log_sum
                        curr_best_node = i
                    i += 1
                    
                viterbi_t.append(viterbi_t_j)
                bp_t.append(curr_best_node)
                j += 1
            viterbi_table.append(viterbi_t)
            bp_table.append(bp_t)
            t += 1
        
        # return alpha_table[1:]
        
        # perform the viterbi back-trace
        t = len(bp_table) - 1
        states_seq = list()
        curr_state = end_state
        while (t > 0):
            states_seq.append(curr_state)
            curr_state = bp_table[t][curr_state]
            t -= 1
        
        states_seq.reverse()
        return states_seq
    

    # hmm = (A, B)
    def forward(self, input_seq, hmm, start_state):
        (A, B) = hmm
        N = len(A)  # number of states
        
        alpha_table = list()
    
        # calculate t=0
        start_entry = list()    
        for i in xrange(0, N):
            if (i == start_state):
                start_entry.append(ln(1.0))
            else:
                start_entry.append(ln(0.0))
        
        alpha_table.append(start_entry)
        
        t = 1
        while (t <= len(input_seq)): # loop through time
            alpha_t = list()
            ot = input_seq[t - 1]   # this is t - 1 because t0 is start state
            j = 0
            while (j < N): # for each state
                i = 0
                alpha_t_j = NEG_INF
                while (i < N): # calculate alpha_t_j
                    log_sum = alpha_table[t - 1][i] + A[i][j] + B[j][ot]
                    alpha_t_j = log_add(alpha_t_j, log_sum)
                    i += 1
                    
                alpha_t.append(alpha_t_j)
                j += 1
            alpha_table.append(alpha_t)
            t += 1
        
        return alpha_table[1:]
    
    def backward(self, input_seq, hmm, end_state):
        (A, B) = hmm
        N = len(A)
        
        beta_table = list()
        
        end_entry = list()
        # follows the paper, not the book
        for i in xrange(0, N):
            if (i == end_state):
                end_entry.append(ln(1.0))
            else:
                end_entry.append(ln(0.0))
    
        # create the beta table in advance since we are filling backwards
        i = 0
        while (i < len(input_seq)):
            beta_t = list()
            j = 0
            while (j < N):
                beta_t.append(ln(0.0))
                j += 1
            
            beta_table.append(beta_t)
            i += 1
        
        beta_table.append(end_entry)
        
        t = len(input_seq) - 1 # starting from 2nd to last and move backwards
        while (t >= 0):
            ot_next = input_seq[t]
            i = 0
            while (i < N):
                j = 0
                beta_t_i = NEG_INF
                while (j < N):
                    log_sum = A[i][j] + B[j][ot_next] + beta_table[t + 1][j]
                    beta_t_i = log_add(beta_t_i, log_sum)
                    j += 1
    
                beta_table[t][i] = beta_t_i
                i += 1
                
            t -= 1
        
        return beta_table[:-1]

    def Xi(self, i, j, t, input_seq, alpha, beta, A, B):
        ot_next = input_seq[t + 1]
        
        numerator = alpha[t][i] + A[i][j] + B[j][ot_next] + beta[t + 1][j]
        denominator = ln(0.0)
        
        ip = 0
        while (ip < len(A)):
            jp = 0
            while (jp < len(A[ip])):
                denominator = log_add(denominator, alpha[t][ip] + A[ip][jp] + B[jp][ot_next] + beta[t + 1][jp])
                jp += 1        
            ip += 1
        
        return (numerator - denominator) 

    def A_prime(self, i, j, Xi_table, gamma_table, input_seq):
        numerator = ln(0.0)
        denominator = ln(0.0)
        
        t = 0
        while (t < len(input_seq) - 1):
            numerator = log_add(numerator, Xi_table[t][i][j])
            denominator = log_add(denominator, gamma_table[t][i])
            t += 1
        
        return (numerator - denominator)
    
    def B_prime(self, j, vk, Xi_table, gamma_table, input_seq):
        numerator = ln(0.0)
        denominator = ln(0.0)
        
        t = 0
        while (t < len(input_seq) - 1):
            if (input_seq[t] == vk):
                numerator = log_add(numerator, gamma_table[t][j])
            denominator = log_add(denominator, gamma_table[t][j])
            t += 1
        
        return (numerator - denominator)
     

    def forward_backward(self, alpha, beta, A, B, input_seq):
        newA = list()
        newB = list()
        
        # first compute Xi_table[t][i][j] and gamma_table[t][i]
        # sys.stderr.write("Calculating Xi, gamma tables ... \n")
        Xi_table = []
        gamma_table = []
        t = 0
        while (t < len(input_seq) - 1):
            i = 0
            Xi_t = []
            gamma_t = []
            while (i < len(A)):
                Xi_t_i = []
                gamma_t_i = ln(0.0)
                j = 0
                
                while (j < len(A[i])):
                    Xi_t_i.append(Xi(i, j, t, input_seq, alpha, beta, A, B))
                    gamma_t_i = log_add(gamma_t_i, Xi_t_i[-1])
                    j += 1
                    
                Xi_t.append(Xi_t_i)
                gamma_t.append(gamma_t_i)
                i += 1
                
            Xi_table.append(Xi_t)
            gamma_table.append(gamma_t)
            t += 1
        
        # sys.stderr.write("Computing new values for A ... \n")
        # compute the new A and B matrices
        i = 0
        while (i < len(A)):
            j = 0
            newA_i = list()
            while (j < len(A[i])):
                newA_i.append(A_prime(i, j, Xi_table, gamma_table, input_seq))
                j += 1
            newA.append(newA_i)
            i += 1
        
        # sys.stderr.write("Computing new values for B ... \n")
        o_list = [' ']
        i = ord('A')
        while (i <= ord('Z')):
            o_list.append(chr(i))
            i += 1
    
        j = 0
        while (j < len(B)):
            newB_j = dict()
            for vk in o_list:
                newB_j[vk] = B_prime(j, vk, Xi_table, gamma_table, input_seq)
            j += 1
            newB.append(newB_j)
        
        return (newA, newB)

def get_A_matrix(use_random):
    A = list()
    if (not use_random):
        # my model
        # store everything in log
        A.append([math.log(0.5709), math.log(0.4291)])
        A.append([math.log(0.8785), math.log(0.1215)])
    else:
        # random model
        rnd1 = random.random()
        rnd2 = random.random()
        A.append([math.log(rnd1), math.log(1.0 - rnd1)])
        A.append([math.log(rnd2), math.log(1.0 - rnd2)])
        
    return A

def get_A_matrix_test():
    A = list()
    A.append(list())

def get_B_matrix(use_random):
    B = list()
    if (not use_random):
        # my model
        dict_v = dict()
        dict_v['A'] = 0.0624406631
        dict_v['E'] = 0.1045059519
        dict_v['I'] = 0.0571094720
        dict_v['O'] = 0.0645950486
        dict_v['U'] = 0.0221280946
        dict_v['Y'] = 0.0173811437
        dict_v[' '] = 0.0
        
        dict_c = dict()
        dict_c[' '] = 0.1834513985
        dict_c['B'] = 0.0118308625
        dict_c['C'] = 0.0190973490
        dict_c['D'] = 0.0369897028
        dict_c['F'] = 0.0211787044
        dict_c['G'] = 0.0128897977
        dict_c['H'] = 0.0547725115
        dict_c['J'] = 0.0008033302
        dict_c['K'] = 0.0032498357
        dict_c['L'] = 0.0319141167
        dict_c['M'] = 0.0232965749
        dict_c['N'] = 0.0564522019
        dict_c['P'] = 0.0113561674
        dict_c['Q'] = 0.0011684802
        dict_c['R'] = 0.0514131308
        dict_c['S'] = 0.0530563061
        dict_c['T'] = 0.0682465493
        dict_c['V'] = 0.0103337472
        dict_c['W'] = 0.0195355291
        dict_c['X'] = 0.0007668152
        dict_c['Z'] = 0.0000365150
        
        # assume 0 prob for non-existing emissions (no smoothing)
        i = ord('A')
        while (i <= ord('Z')):
            if (chr(i) not in dict_v.keys()):
                dict_v[chr(i)] = 0.0
            if (chr(i) not in dict_c.keys()):
                dict_c[chr(i)] = 0.0
            i += 1
        
        # store everything in log
        for k in dict_c.keys():
            dict_c[k] = ln(dict_c[k])
    
        for k in dict_v.keys():
            dict_v[k] = ln(dict_v[k])
        
        B.append(dict_c)
        B.append(dict_v)
    else:
        # use random model
        i = 0
        while (i < 2):
            b_dict = dict()
            
            c_list = [' ']
            j = ord('A')
            while (j <= ord('Z')):
                c_list.append(chr(j))
                j += 1
            psum = 0.0
            for c in c_list:
                b_dict[c] = random.random()
                psum += b_dict[c]
            
            # normalize and convert to log
            for c in c_list:
                b_dict[c] = math.log(float(b_dict[c]) / float(psum))
            
            B.append(b_dict)
            i += 1
        
    return B

    def print_tables(self, T, input_seq):
        i = 0
        while (i < len(T)):
            sys.stdout.write("%d:\t"%(i))
            j = 0
            while (j < len(T[i])):
                sys.stdout.write("%.10f "%(T[i][j]))
                j += 1
            print input_seq[i]
            i += 1


    def get_avgll_by_alpha(self, alpha_table, input_seq, end_state):
        return alpha_table[-1][end_state] / float(len(input_seq))


    def print_A(self, A):
        i = 0
        while (i < len(A)):
            j = 0
            while (j < len(A[i])):
                sys.stdout.write("[%d,%d]\t%.15f\t"%(i, j, math.exp(A[i][j])))
                j += 1
            print        
            i += 1
    
    def print_B(self, B):
        i = 0
        while (i < len(B)):
            print
            for c in B[i].keys():
                sys.stdout.write("%s\t%.15f\n"%(c, math.exp(B[i][c])))
            i += 1

    def dump_to_file(self, A, B, alpha_table, beta_table):
        table_file = open("tables.dump", "w")
        pickle.dump((A, B, alpha_table, beta_table), table_file)
        table_file.close()
    
    def load_from_file(self, ):
        table_file = open("tables.dump", "r")
        tables = pickle.load(table_file)
        table_file.close()
        return tables

    
def main():
    random.seed()
    A = get_A_matrix(True)
    B = get_B_matrix(True)
    
    input_seq = "" # we'll skip the first emission
    # for line in sys.stdin:
    #     input_seq += line.replace("\n", "")
    
    # finput = open("../hmm-train.clean.txt", "r")
    # finput = open("../hmm-test.clean.txt", "r")
    finput = open("../hmm-decode.clean.txt", "r")
    
    for line in finput.readlines():
        input_seq += line.replace("\n", "")
    finput.close()
    
    alpha_table = forward(input_seq, (A, B), 0)
    beta_table = backward(input_seq, (A, B), 0)
    
    (A, B, alpha_table, beta_table) = load_from_file()
    
    # do viterbi search
    if (True):
        states_seq = viterbi(input_seq, (A, B), 0, 0)
        # states_seq = bad_decode(input_seq, (A, B))
        for s in states_seq:
            sys.stdout.write("%s"%(s))
        print
        return

    iteration = 0
    prev_avg_ll = NEG_INF
    while (iteration < 1):
        avg_ll = get_avgll_by_alpha(alpha_table, input_seq, 0)                
        sys.stdout.write("avg_ll iteration %d:\t%.10f\n"%(iteration, avg_ll))
        sys.stderr.write("avg_ll iteration %d:\t%.10f\n"%(iteration, avg_ll))
        
        if (avg_ll < prev_avg_ll):
            sys.stderr.write("Ended.\n")
            break
        prev_avg_ll = avg_ll
        
        # dump_to_file(A, B, alpha_table, beta_table)
        
        (newA, newB) = forward_backward(alpha_table, beta_table, A, B, input_seq)
        A = newA
        B = newB

        alpha_table = forward(input_seq, (newA, newB), 0)
        beta_table = backward(input_seq, (newA, newB), 0)

        
        #print "A table iteration %d"%(iteration)
        #print_A(A)
        #print "B table iteration %d"%(iteration)
        #print_B(B)
        
        # sys.stdout.write("Alpha tables: \n")
        # print_tables(alpha_table, input_seq)
    
        # sys.stdout.write("Beta tables: \n")
        # print_tables(beta_table, input_seq)
        
        iteration += 1
    

if __name__ == "__main__":
    main()
