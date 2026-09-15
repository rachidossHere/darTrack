import { ResolveFn } from '@angular/router';
import { inject } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Project } from '../../shared/models/models';

export const projectResolver: ResolveFn<Project[]> = () => inject(ApiService).projects();
