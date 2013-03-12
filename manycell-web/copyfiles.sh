#!/bin/bash

HOST='bernie.mib.manchester.ac.uk'
USER='joseph'

scp test.txt $USER@$HOST:/home/$USER/local/manycell/
ssh $USER@$HOST /home/$USER/local/user-workspace/manycell/manycell.sh

ssh $USER@$HOST mkdir /home/$USER/local/user-workspace/manycell/dadaDIR

